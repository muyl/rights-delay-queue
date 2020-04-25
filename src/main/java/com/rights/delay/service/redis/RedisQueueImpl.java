package com.rights.delay.service.redis;

import com.rights.delay.common.exception.DelayQueueException;
import com.rights.delay.common.util.NamedUtil;
import com.rights.delay.domain.Status;
import com.rights.delay.service.queue.Queue;
import com.rights.delay.service.redis.support.RedisQueueProperties;
import com.rights.delay.domain.JobMessage;
import com.rights.delay.service.redis.bucket.BucketQueueManager;
import com.rights.delay.service.redis.ready.ReadyQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 拓仲 on 2020/3/10
 */
public class RedisQueueImpl implements Queue, java.io.Closeable {
    public static final Logger LOGGER = LoggerFactory.getLogger(RedisQueueImpl.class);

    private volatile AtomicBoolean isRunning = new AtomicBoolean(false);
    private volatile AtomicInteger pos = new AtomicInteger(0);


    private JobOperationService jobOperationService;

    private RedisQueueProperties properties;

    private BucketQueueManager bucketQueueManager;

    private ReadyQueueManager readyQueueManager;


    @Override
    public void push(JobMessage job) throws DelayQueueException {
        try {
            if (job.getStatus() != Status.WaitPut.ordinal() && job.getStatus() != Status.Restore.ordinal()) {
                throw new IllegalArgumentException(String.format("任务%s状态异常", job.getId()));
            }
            String queueName = buildQueueName();
            this.jobOperationService.addJobToPool(job);
            double score = job.getCreateTime() + job.getDelay();

            this.jobOperationService.addBucketJob(queueName, job.getId(), score);
            job.setStatus(Status.Delay.ordinal());
            this.jobOperationService.updateJobStatus(job.getId(), Status.Delay);
        } catch (Exception e) {
            LOGGER.error("添加任务失败", e);
            throw new DelayQueueException(e);
        }

    }

    /**
     * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String convertTimeToString(Long time) {
        Assert.notNull(time, "time is null");
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }


    @Override
    public void clear() {

    }

    @Override
    public boolean delete(String jobId) {
        return false;
    }

    @Override
    public JobMessage getJob(String jobId) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            bucketQueueManager.start();
            readyQueueManager.start();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }


    public void setJobOperationService(JobOperationService jobOperationService) {
        this.jobOperationService = jobOperationService;
    }

    public void setProperties(RedisQueueProperties properties) {
        this.properties = properties;
    }

    public void setBucketQueueManager(BucketQueueManager bucketQueueManager) {
        this.bucketQueueManager = bucketQueueManager;
    }

    public void setReadyQueueManager(ReadyQueueManager readyQueueManager) {
        this.readyQueueManager = readyQueueManager;
    }
    //=================================================================================

    /**
     * 根据BuckSize轮询获取
     */
    public String buildQueueName() {
        return NamedUtil.buildBucketName(properties.getPrefix(), properties.getName(), getNextRoundRobin());
    }

    /**
     * 轮询算法  目前只适用于单机
     */
    private int getNextRoundRobin() {
        synchronized (this) {
            if (pos.get() >= properties.getBucketSize() || pos.get() < 0) {
                pos.set(1);
            } else {
                pos.getAndIncrement();
            }
        }
        return pos.get();
    }
}
