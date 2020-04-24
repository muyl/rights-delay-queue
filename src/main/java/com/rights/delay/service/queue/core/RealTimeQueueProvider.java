package com.rights.delay.service.queue.core;

import com.rights.delay.common.exception.ConsumeQueueException;
import com.rights.delay.common.extension.SPI;
import com.rights.delay.domain.JobMessage;

/**
 * @author 拓仲 on 2020/3/11
 */
@SPI("consoleCQ")
public interface RealTimeQueueProvider {

    /**
     * 发送消息
     * @param jobMessage 消息
     * @throws ConsumeQueueException
     */
    void sendMessage(JobMessage jobMessage) throws ConsumeQueueException;
}
