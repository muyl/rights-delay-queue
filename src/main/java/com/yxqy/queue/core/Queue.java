package com.yxqy.queue.core;

import com.yxqy.domain.JobMsg;
import com.yxqy.exception.DelayQueueException;
import com.yxqy.queue.Lifecycle;

/**
 * @author 拓仲 on 2020/3/10
 */
public interface Queue extends Lifecycle {

    /**
     * 放入延迟消息
     *
     * @param job 消息
     * @throws DelayQueueException 异常
     */
    void push(JobMsg job) throws DelayQueueException;

    /**
     * 清空任务
     */
    void clear();

    /**
     * 删除延迟任务
     *
     * @param jobId 任务消息编号
     * @return 删除标识
     */
    boolean delete(String jobId);

    /**
     * 获取任务信息
     *
     * @param jobId 任务消息编号
     * @return 任务信息
     */
    JobMsg getJob(String jobId);
}
