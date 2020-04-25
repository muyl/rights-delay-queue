package com.rights.delay.service.redis.ready;

import com.rights.delay.common.util.DateUtils;
import com.rights.delay.common.util.NamedUtil;
import com.rights.delay.common.util.UUIDUtils;
import com.rights.delay.domain.Status;
import com.rights.delay.service.queue.core.RealTimeQueueProvider;
import com.rights.delay.service.queue.Queue;
import com.rights.delay.service.redis.JobOperationService;
import com.rights.delay.service.redis.event.JobEventBus;
import com.rights.delay.service.redis.event.RedisJobTraceEvent;
import com.rights.delay.service.redis.support.DistributedLock;
import com.rights.delay.service.redis.support.RedisQueueProperties;
import com.rights.delay.domain.JobMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 拓仲 on 2020/3/11
 */
public class RealTimeTask extends Thread {

    public static final Logger LOGGER = LoggerFactory.getLogger(RealTimeTask.class);

    private RedisQueueProperties properties;
    private JobOperationService jobOperationService;
    private Queue delayQueue;
    private DistributedLock lock = null;
    private RealTimeQueueProvider realTimeQueueProvider = null;

    @Override
    public void run() {
        if (properties.isCluster()) {
            String lockName = NamedUtil.buildLockName(NamedUtil.buildRealTimeName(properties.getPrefix(), properties
                    .getName(), properties.getReadyName()));
            String requestId = UUIDUtils.getUUID();
            try {
                if (lock.tryLock(lockName,requestId)){
                    runInstance();
                }
            }finally{
                lock.unlock(lockName);
            }
        } else {
            runInstance();
        }

    }

    private void runInstance() {
        try {
            //获取ready队列中的一个数据
            List<String> jobIds = jobOperationService.getReadyJob(10);
            if (jobIds != null && jobIds.size() > 0) {
                for (String jobId : jobIds) {
                    if (!StringUtils.isEmpty(jobId)) {
                        JobMessage j = jobOperationService.getJob(jobId);
                        if (j == null) {
                            this.jobOperationService.removeReadyJob(jobId);
                            LOGGER.warn("任务ID {} 元数据池没有数据", jobId);
                            continue;
                        }
                        if (j.getStatus() == Status.Delete.ordinal()) {
                            this.jobOperationService.removeJobToPool(jobId);
                            continue;
                        }
                        if (j.getStatus() != Status.Delete.ordinal()) {
                            //没有达到执行时间 从新发送延时Buck中
                            if (!check(j)) {
                                j.setStatus(Status.Restore.ordinal());
                                delayQueue.push(j);
                                continue;
                            }
                            if (LOGGER.isInfoEnabled()) {
                                long runLong = j.getDelay() + j.getCreateTime();
                                String runDateString = DateUtils.format(new Date(runLong), DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
                                LOGGER.info(String.format("invokeTask %s target time : %s", jobId, runDateString));
                            }
                            realTimeQueueProvider.sendMessage(j);
                            j.setStatus(Status.Finish.ordinal());
                            this.jobOperationService.updateJobStatus(jobId, Status.Finish);
                            this.jobOperationService.removeReadyJob(jobId);
                            this.jobOperationService.removeJobToPool(jobId);
                            JobEventBus.getInstance().post(new RedisJobTraceEvent(j));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("处理实时队列发生错误", e);
        }
    }

    private boolean check(JobMessage job) {
        long runTime = job.getCreateTime() + job.getDelay(), currentTime = System.currentTimeMillis();
        return runTime <= currentTime;
    }


    public void setProperties(RedisQueueProperties properties) {
        this.properties = properties;
    }

    public void setJobOperationService(JobOperationService jobOperationService) {
        this.jobOperationService = jobOperationService;
    }

    /**
     * 注入队列操作对象 便于时间没有到触发条件下 执行重新发送
     */
    public void setDelayQueue(Queue delayQueue) {
        this.delayQueue = delayQueue;
    }

    public void setLock(DistributedLock lock) {
        this.lock = lock;
    }

    public void setRealTimeQueueProvider(RealTimeQueueProvider realTimeQueueProvider) {
        this.realTimeQueueProvider = realTimeQueueProvider;
    }


}
