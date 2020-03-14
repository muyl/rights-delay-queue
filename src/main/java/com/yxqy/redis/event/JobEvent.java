package com.yxqy.redis.event;

import com.yxqy.domain.JobMessage;

/**
 * @author 拓仲 on 2020/3/11
 */
public interface JobEvent {

    JobMessage getJob();
}
