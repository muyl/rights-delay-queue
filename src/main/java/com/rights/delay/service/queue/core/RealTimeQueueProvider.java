package com.rights.delay.service.queue.core;

import com.rights.delay.common.exception.ConsumeQueueException;
import com.rights.delay.domain.JobMessage;

/**
 * Real time queue provider
 *
 * @author 拓仲 on 2020/3/11
 */
public interface RealTimeQueueProvider {

    /**
     * 发送消息
     *
     * @param jobMessage 消息
     * @throws ConsumeQueueException consume queue exception
     */
    void sendMessage(JobMessage jobMessage) throws ConsumeQueueException;
}
