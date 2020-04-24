package com.yxqy.delay.service.queue.core;

import com.yxqy.delay.common.extension.SPI;
import com.yxqy.delay.domain.JobMessage;
import com.yxqy.delay.common.exception.ConsumeQueueException;

/**
 * @author 拓仲 on 2020/3/11
 */
@SPI("consoleCQ")
public interface ConsumeQueueProvider {
    void consumer(JobMessage j) throws ConsumeQueueException;
}
