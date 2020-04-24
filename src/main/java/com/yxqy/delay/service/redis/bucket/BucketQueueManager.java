package com.yxqy.delay.service.redis.bucket;

import com.yxqy.delay.common.util.NamedUtil;
import com.yxqy.delay.service.queue.Lifecycle;
import com.yxqy.delay.service.redis.JobOperationService;
import com.yxqy.delay.service.redis.support.DistributedLock;
import com.yxqy.delay.service.redis.support.RedisQueueProperties;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 拓仲 on 2020/3/10
 */
public class BucketQueueManager implements Lifecycle {

    public static final Logger LOGGER = LoggerFactory.getLogger(BucketQueueManager.class);

    public static final String THREAD_NAME = "ccmq-delay-queue-%s";

    private volatile AtomicBoolean isRunning = new AtomicBoolean(false);

    private ScheduledThreadPoolExecutor executor;

    @Setter
    private RedisQueueProperties properties;
    @Setter
    private JobOperationService  jobOperationService;
    @Setter
    private DistributedLock      lock;

    /** 是否为守护线程 */
    public boolean daemon = true;

    @Override
    public void start() {
        int bucketSize = checkBucketNum(properties.getBucketSize());
        if (isRunning.compareAndSet(false, true)) {
            executor = new ScheduledThreadPoolExecutor(bucketSize);
            for (int i = 1; i <= bucketSize; i++) {
                String bName = NamedUtil.buildBucketName(properties.getPrefix(), properties.getName(), i);
                BucketTask task = new BucketTask(bName);
                task.setJobOperationService(jobOperationService);
                task.setPoolName(NamedUtil.buildPoolName(properties.getPrefix(), properties.getName(), properties
                        .getOriginPool()));
                task.setReadyName(NamedUtil.buildPoolName(properties.getPrefix(), properties.getName(), properties
                        .getReadyName()));
                task.setProperties(properties);
                task.setLock(lock);
                this.executor.scheduleAtFixedRate(task, 5,
                        10, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            executor.shutdown();
            LOGGER.info("stoping timer  .....");
        }

    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }


    private int checkBucketNum(int bucketSize) {
        if (bucketSize <= 0) {
            bucketSize = 1;
        }
        return bucketSize;
    }
}
