package com.rights.delay.service.redis.event;

import com.google.common.eventbus.Subscribe;

/**
 * Job event listener
 *
 * @author 拓仲 on 2020/3/11
 */
public interface JobEventListener {

    /**
     * Listen *
     *
     * @param event event
     */
    @Subscribe
    void listen(JobEvent event);
}
