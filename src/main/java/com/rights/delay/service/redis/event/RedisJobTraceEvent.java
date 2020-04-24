package com.rights.delay.service.redis.event;

import com.rights.delay.domain.JobMessage;
import com.rights.delay.domain.RdbOperation;

/**
 * @author 拓仲 on 2020/3/11
 */
public class  RedisJobTraceEvent implements JobEvent {

    private JobMessage   jobMessage = null;
    private RdbOperation operation  = RdbOperation.UPDATE;

    public RedisJobTraceEvent(JobMessage jobMessage) {
        this.jobMessage = jobMessage;
    }

    public RedisJobTraceEvent(JobMessage jobMessage, RdbOperation operation) {
        this.jobMessage = jobMessage;
        this.operation = operation;
    }

    @Override
    public JobMessage getJob() {
        return jobMessage;
    }

    public RdbOperation getOperation() {
        return operation;
    }
}
