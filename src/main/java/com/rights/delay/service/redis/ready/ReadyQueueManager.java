package com.rights.delay.service.redis.ready;

import com.rights.delay.service.queue.Lifecycle;
import com.rights.delay.service.queue.core.RealTimeQueueProvider;
import com.rights.delay.service.queue.Queue;
import com.rights.delay.service.redis.JobOperationService;
import com.rights.delay.service.redis.support.DistributedLock;
import com.rights.delay.service.redis.support.RedisQueueProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Ready queue manager
 *
 * @author 拓仲 on 2020/3/10
 */
public class ReadyQueueManager implements Lifecycle {

    /**
     * LOGGER
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(ReadyQueueManager.class);
    /**
     * Daemon
     */
    public boolean daemon = true;
    private volatile AtomicBoolean isRuning = new AtomicBoolean(false);
    private RedisQueueProperties properties;
    private ScheduledThreadPoolExecutor executor;
    private JobOperationService jobOperationService;
    private Queue delayQueue;
    private DistributedLock lock = null;
    private RealTimeQueueProvider realTimeQueueProvider;


    @Override
    public void init() {

    }

    @Override
    public void start() {
        if (isRuning.compareAndSet(false, true)) {
            ReadyThreadFactory threadFactory = new ReadyThreadFactory();
            executor = new ScheduledThreadPoolExecutor(1, threadFactory);
            RealTimeTask task = new RealTimeTask();
            task.setProperties(properties);
            task.setJobOperationService(jobOperationService);
            task.setDelayQueue(delayQueue);
            task.setLock(lock);
            task.setRealTimeQueueProvider(realTimeQueueProvider);
            task.setDaemon(daemon);
            executor.scheduleAtFixedRate(task, 3000, properties.getReadyRoundRobinTime(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void stop() {
        if (isRuning.compareAndSet(true, false)) {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }

    @Override
    public boolean isRunning() {
        return isRuning.get();
    }

    /**
     * Sets properties *
     *
     * @param properties properties
     */
    public void setProperties(RedisQueueProperties properties) {
        this.properties = properties;
    }

    /**
     * Sets daemon *
     *
     * @param daemon daemon
     */
    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    /**
     * Sets delay queue *
     *
     * @param delayQueue delay queue
     */
    public void setDelayQueue(Queue delayQueue) {
        this.delayQueue = delayQueue;
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
     * Sets lock *
     *
     * @param lock lock
     */
    public void setLock(DistributedLock lock) {
        this.lock = lock;
    }

    /**
     * Sets real time queue provider *
     *
     * @param realTimeQueueProvider real time queue provider
     */
    public void setRealTimeQueueProvider(RealTimeQueueProvider realTimeQueueProvider) {
        this.realTimeQueueProvider = realTimeQueueProvider;
    }
}
