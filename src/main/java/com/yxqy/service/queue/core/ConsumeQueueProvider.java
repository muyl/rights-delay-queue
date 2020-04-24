package com.yxqy.service.queue.core;

import com.yxqy.common.extension.SPI;
import com.yxqy.domain.JobMessage;
import com.yxqy.common.exception.ConsumeQueueException;

/**
 * @author 拓仲 on 2020/3/11
 */
@SPI("consoleCQ")
public interface ConsumeQueueProvider {
    void consumer(JobMessage j) throws ConsumeQueueException;
}
