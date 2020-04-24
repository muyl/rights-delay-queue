package com.rights.delay.service.redis.event;

import com.google.common.eventbus.Subscribe;

/**
 * @author 拓仲 on 2020/3/11
 */
public interface JobEventListener {

    @Subscribe
    void listen(JobEvent event);
}
