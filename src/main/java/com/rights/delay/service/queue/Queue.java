package com.rights.delay.service.queue;

import com.rights.delay.common.exception.DelayQueueException;
import com.rights.delay.domain.JobMessage;
import com.rights.delay.service.queue.Lifecycle;

/**
 * Queue
 *
 * @author 拓仲 on 2020/3/10
 */
public interface Queue extends Lifecycle {

    /**
     * 放入延迟消息
     *
     * @param job 消息
     * @throws DelayQueueException 异常
     */
    void push(JobMessage job) throws DelayQueueException;

    /**
     * 清空任务
     */
    void clear();

    /**
     * 删除延迟任务
     *
     * @param jobId 任务消息编号
     * @return 删除标识 boolean
     */
    boolean delete(String jobId);

    /**
     * 获取任务信息
     *
     * @param jobId 任务消息编号
     * @return 任务信息 job
     */
    JobMessage getJob(String jobId);
}
