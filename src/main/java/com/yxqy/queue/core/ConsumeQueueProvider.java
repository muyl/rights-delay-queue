package com.yxqy.queue.core;

import com.yxqy.common.extension.SPI;
import com.yxqy.domain.JobMsg;
import com.yxqy.exception.ConsumeQueueException;

/**
 * @author 拓仲 on 2020/3/11
 */
@SPI("consoleCQ")
public interface ConsumeQueueProvider {
    void consumer(JobMsg j) throws ConsumeQueueException;
}
