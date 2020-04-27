package com.rights.delay.service.redis.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Job event bus
 *
 * @author 拓仲 on 2020/3/11
 */
public class JobEventBus {

    private EventBus      bus      = null;
    private AtomicBoolean register = new AtomicBoolean(false);

    private JobEventBus() {
        bus = new AsyncEventBus(MoreExecutors.newDirectExecutorService());
    }

    /**
     * Gets instance *
     *
     * @return the instance
     */
    public static JobEventBus getInstance() {
        return LazyHolder.JEB;
    }

    /**
     * Register *
     *
     * @param listener listener
     */
    public void register(JobEventListener listener) {
        if (register.compareAndSet(false, true)) {
            bus.register(listener);
        }

    }

    /**
     * Unregister *
     *
     * @param listener listener
     */
    public void unregister(JobEventListener listener) {
        if (register.get() == true) {
            bus.unregister(listener);
        }
    }

    /**
     * Post *
     *
     * @param event event
     */
    public void post(JobEvent event) {
        if (register.get() == true) {
            bus.post(event);
        }

    }

    private static class LazyHolder {

        private static final JobEventBus JEB = new JobEventBus();
    }
}
