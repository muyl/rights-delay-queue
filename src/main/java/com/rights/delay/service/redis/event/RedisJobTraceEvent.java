package com.rights.delay.service.redis.event;

import com.rights.delay.domain.JobMessage;
import com.rights.delay.domain.RdbOperation;

/**
 * Redis job trace event
 *
 * @author 拓仲 on 2020/3/11
 */
public class  RedisJobTraceEvent implements JobEvent {

    private JobMessage   jobMessage = null;
    private RdbOperation operation  = RdbOperation.UPDATE;

    /**
     * Redis job trace event
     *
     * @param jobMessage job message
     */
    public RedisJobTraceEvent(JobMessage jobMessage) {
        this.jobMessage = jobMessage;
    }

    /**
     * Redis job trace event
     *
     * @param jobMessage job message
     * @param operation operation
     */
    public RedisJobTraceEvent(JobMessage jobMessage, RdbOperation operation) {
        this.jobMessage = jobMessage;
        this.operation = operation;
    }

    @Override
    public JobMessage getJob() {
        return jobMessage;
    }

    /**
     * Gets operation *
     *
     * @return the operation
     */
    public RdbOperation getOperation() {
        return operation;
    }
}
