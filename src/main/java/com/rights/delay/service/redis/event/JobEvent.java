package com.rights.delay.service.redis.event;

import com.rights.delay.domain.JobMessage;

/**
 * @author 拓仲 on 2020/3/11
 */
public interface JobEvent {

    JobMessage getJob();
}
