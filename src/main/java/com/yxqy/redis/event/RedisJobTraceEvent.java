package com.yxqy.redis.event;

import com.yxqy.domain.JobMsg;
import com.yxqy.domain.RdbOperation;

/**
 * @author 拓仲 on 2020/3/11
 */
public class  RedisJobTraceEvent implements JobEvent {

    private JobMsg       jobMsg    = null;
    private RdbOperation operation = RdbOperation.UPDATE;

    public RedisJobTraceEvent(JobMsg jobMsg) {
        this.jobMsg = jobMsg;
    }

    public RedisJobTraceEvent(JobMsg jobMsg, RdbOperation operation) {
        this.jobMsg = jobMsg;
        this.operation = operation;
    }

    @Override
    public JobMsg getJob() {
        return jobMsg;
    }

    public RdbOperation getOperation() {
        return operation;
    }
}
