package com.rights.delay.service.queue.cqp;

import com.rights.delay.common.exception.ConsumeQueueException;
import com.rights.delay.domain.JobMessage;
import com.rights.delay.service.queue.core.RealTimeQueueProvider;
import com.rights.delay.common.util.JsonUtil;

/**
 * Real time queue consumer
 *
 * @author 拓仲 on 2020/3/11
 */
public class RealTimeQueueConsumer implements RealTimeQueueProvider {


    @Override
    public void sendMessage(JobMessage job) throws ConsumeQueueException {
        System.out.println(String.format("invoke topic %s json:%s", job.getTopic(),
                JsonUtil.convertObjectToJSON(job)));
    }

}
