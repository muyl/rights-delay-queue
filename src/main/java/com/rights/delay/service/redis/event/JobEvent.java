package com.rights.delay.service.redis.event;

import com.rights.delay.domain.JobMessage;

/**
 * Job event
 *
 * @author 拓仲 on 2020/3/11
 */
public interface JobEvent {

    /**
     * Gets job *
     *
     * @return the job
     */
    JobMessage getJob();
}
