package com.rights.delay.service.redis.bucket;

import com.rights.delay.common.util.NamedUtil;
import com.rights.delay.service.queue.Lifecycle;
import com.rights.delay.service.redis.JobOperationService;
import com.rights.delay.service.redis.support.DistributedLock;
import com.rights.delay.service.redis.support.RedisQueueProperties;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Bucket queue manager
 *
 * @author 拓仲 on 2020/3/10
 */
public class BucketQueueManager implements Lifecycle {

    /**
     * LOGGER
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(BucketQueueManager.class);

    private volatile AtomicBoolean isRunning = new AtomicBoolean(false);

    private ScheduledThreadPoolExecutor executor;


    @Setter
    private RedisQueueProperties properties;
    @Setter
    private JobOperationService jobOperationService;
    @Setter
    private DistributedLock lock;

    /** 是否为守护线程 */
    public boolean daemon = true;

    @Override
    public void init() {

    }

    @Override
    public void start() {
        int bucketSize = checkBucketNum(properties.getBucketSize());
        if (isRunning.compareAndSet(false, true)) {
            ThreadFactory threadFactory = new BucketThreadFactory();
            executor = new ScheduledThreadPoolExecutor(bucketSize, threadFactory);
            for (int i = 1; i <= bucketSize; i++) {
                String bName = NamedUtil.buildBucketName(properties.getPrefix(), properties.getName(), i);
                BucketTask task = new BucketTask(bName);
                task.setJobOperationService(jobOperationService);
                task.setPoolName(NamedUtil.buildPoolName(properties.getPrefix(), properties.getName(), properties.getOriginPool()));
                task.setReadyName(NamedUtil.buildPoolName(properties.getPrefix(), properties.getName(), properties.getReadyName()));
                task.setProperties(properties);
                task.setLock(lock);
                task.setDaemon(daemon);
                this.executor.scheduleAtFixedRate(task, 5, 10, TimeUnit.SECONDS);
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
