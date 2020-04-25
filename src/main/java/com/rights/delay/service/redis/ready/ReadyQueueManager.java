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
 * @author 拓仲 on 2020/3/10
 */
public class ReadyQueueManager implements Lifecycle {

    public static final Logger LOGGER = LoggerFactory.getLogger(ReadyQueueManager.class);

    public static final String THREAD_NAME = "sdmq-ready-queue-%s";
    public boolean daemon = true;
    private volatile AtomicBoolean isRuning = new AtomicBoolean(false);
    private RedisQueueProperties properties;
    private ScheduledThreadPoolExecutor executor;
    private JobOperationService jobOperationService;
    private Queue delayQueue;
    private String threadName;
    private DistributedLock lock = null;
    private RealTimeQueueProvider realTimeQueueProvider;


    @Override
    public void init() {

    }

    @Override
    public void start() {
        if (isRuning.compareAndSet(false, true)) {
            threadName = String.format(THREAD_NAME, 1);
            ReadyThreadFactory threadFactory = new ReadyThreadFactory(threadName);
            executor = new ScheduledThreadPoolExecutor(1, threadFactory);
            RealTimeTask task = new RealTimeTask();
            task.setProperties(properties);
            task.setJobOperationService(jobOperationService);
            task.setDelayQueue(delayQueue);
            task.setLock(lock);
            task.setRealTimeQueueProvider(realTimeQueueProvider);
            task.setDaemon(daemon);
            executor.scheduleAtFixedRate(task, 3000, properties.getReadyRoundRobinTime(), TimeUnit.MILLISECONDS);
            LOGGER.info(String.format("Starting Ready Thead %s ....", threadName));
        }
    }

    @Override
    public void stop() {
        if (isRuning.compareAndSet(true, false)) {
            if (executor != null) {
                executor.shutdown();
                LOGGER.info(String.format("stoping timer %s .....", threadName));
            }
        }
    }

    @Override
    public boolean isRunning() {
        return isRuning.get();
    }

    public void setProperties(RedisQueueProperties properties) {
        this.properties = properties;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public void setDelayQueue(Queue delayQueue) {
        this.delayQueue = delayQueue;
    }

    public void setJobOperationService(JobOperationService jobOperationService) {
        this.jobOperationService = jobOperationService;
    }

    public void setLock(DistributedLock lock) {
        this.lock = lock;
    }

    public void setRealTimeQueueProvider(RealTimeQueueProvider realTimeQueueProvider) {
        this.realTimeQueueProvider = realTimeQueueProvider;
    }
}
