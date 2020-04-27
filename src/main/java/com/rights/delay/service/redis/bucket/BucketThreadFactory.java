package com.rights.delay.service.redis.bucket;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Bucket thread factory
 *
 * @author 拓仲 on 2020/4/25
 */
public class BucketThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    /**
     * Bucket thread factory
     */
    public BucketThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.namePrefix = "bucket-delay-queue-" + threadNumber.getAndIncrement();

    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        //守护线程
        if (t.isDaemon()) {
            t.setDaemon(true);
        }
        return t;
    }
}
