package com.yxqy.redis.ready;

import com.yxqy.common.extension.ExtensionLoader;
import com.yxqy.queue.core.ConsumeQueueProvider;
import com.yxqy.queue.Lifecycle;
import com.yxqy.queue.core.Queue;
import com.yxqy.redis.JobOperationService;
import com.yxqy.redis.support.DistributedLock;
import com.yxqy.redis.support.RedisQueueProperties;
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

    public static final String                      THREAD_NAME = "sdmq-ready-queue-%s";
    public              boolean                     daemon      = true;
    private volatile    AtomicBoolean               isRuning    = new AtomicBoolean(false);
    private             RedisQueueProperties        properties;
    private             ScheduledThreadPoolExecutor executor;
    private             JobOperationService         jobOperationService;
    private             Queue                       delayQueue;
    private             String                      threadName;
    private             DistributedLock             lock        = null;


    @Override
    public void start() {
        if (isRuning.compareAndSet(false, true)) {
            executor = new ScheduledThreadPoolExecutor(1);
            RealTimeTask task = new RealTimeTask();
            task.setProperties(properties);
            task.setJobOperationService(jobOperationService);
            task.setDelayQueue(delayQueue);
            task.setLock(lock);
            task.setConsumeQueueProvider(ExtensionLoader.getExtension(ConsumeQueueProvider.class));
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


}
