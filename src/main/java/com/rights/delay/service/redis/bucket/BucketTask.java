package com.rights.delay.service.redis.bucket;

import com.rights.delay.common.util.NamedUtil;
import com.rights.delay.common.util.UUIDUtils;
import com.rights.delay.domain.Status;
import com.rights.delay.service.redis.JobOperationService;
import com.rights.delay.service.redis.event.JobEventBus;
import com.rights.delay.service.redis.event.RedisJobTraceEvent;
import com.rights.delay.service.redis.support.DistributedLock;
import com.rights.delay.service.redis.support.RedisQueueProperties;
import com.rights.delay.domain.JobMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Bucket task
 *
 * @author 拓仲 on 2020/3/10
 */
public class BucketTask extends Thread {
    /**
     * LOGGER
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(BucketTask.class);

    /**
     * TIME_OUT
     */
    public static final long TIME_OUT = 1000 * 30;

    private String bucketName;

    private JobOperationService jobOperationService;
    private String poolName;
    private String readyName;
    private DistributedLock lock = null;
    private RedisQueueProperties properties;


    /**
     * Bucket task
     *
     * @param bucketName bucket name
     */
    public BucketTask(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public void run() {
        if (properties.isCluster()) {
            String lockName = NamedUtil.buildLockName(bucketName);
            String requestId = UUIDUtils.getUUID();
            try {
                if (lock.tryLock(lockName, requestId)) {
                    runInstance();
                }
            } finally {
                lock.unLock(lockName, requestId);
            }
        } else {
            runInstance();
        }
    }


    private void runInstance() {
        try {
            LOGGER.debug(String.format("开始轮询...%s", bucketName));
            List<String> jobs = peeks();
            if (jobs != null && jobs.size() > 0) {
                for (String jobId : jobs) {
                    if (!StringUtils.isEmpty(jobId)) {
                        JobMessage job = jobOperationService.getJob(jobId);
                        if (null == job) {
                            jobOperationService.removeBucketKey(bucketName, jobId);
                            continue;
                        }
                        if (job.getStatus() == Status.Delete.ordinal()) {
                            this.jobOperationService.removeJobToPool(jobId);
                            this.jobOperationService.removeBucketKey(bucketName, jobId);
                            continue;
                        }
                        //不是延迟状态的数据 不处理 以防数据被消费了 状态还没有更新的问题
                        if (job.getStatus() != Status.Delay.ordinal()) {
                            continue;
                        }
                        long delay = job.getCreateTime() + job.getDelay() - System.currentTimeMillis();
                        //到了当前时间了 把数据从buck移除 并添加到实时队列
                        if (delay <= 0) {
                            job.setStatus(Status.Ready.ordinal());
                            //首先执行修改元数据状态 避免timer竞争到数据了 而状态不一致而出现问题。
                            JobEventBus.getInstance().post(new RedisJobTraceEvent(job));
                            this.jobOperationService.updateJobStatus(job.getId(), Status.Ready);
                            jobOperationService.addReadyTime(readyName, jobId);
                            jobOperationService.removeBucketKey(bucketName, jobId);
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug(String.format("jobId %s 达到了运行时间了", jobId));
                            }
                        } else {//还没有到当前时间
                            //SKIP
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("处理延时队 %s 列发生错误", bucketName), e);
        }
    }

    private String peek() {
        try {
            String jobMsgId = jobOperationService.getBucketTop1Job(bucketName);
            return jobMsgId;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 批量偷取 提高实时性
     */
    private List<String> peeks() {
        try {
            List<String> lst = jobOperationService.getBucketTopJobs(bucketName, 10);
            return lst;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * Sets job operation service *
     *
     * @param jobOperationService job operation service
     */
    public void setJobOperationService(JobOperationService jobOperationService) {
        this.jobOperationService = jobOperationService;
    }

    /**
     * Sets pool name *
     *
     * @param poolName pool name
     */
    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    /**
     * Sets ready name *
     *
     * @param readyName ready name
     */
    public void setReadyName(String readyName) {
        this.readyName = readyName;
    }

    /**
     * Sets lock *
     *
     * @param lock lock
     */
    public void setLock(DistributedLock lock) {
        this.lock = lock;
    }

    /**
     * Sets properties *
     *
     * @param properties properties
     */
    public void setProperties(RedisQueueProperties properties) {
        this.properties = properties;
    }
}
