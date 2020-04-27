package com.rights.delay.service.queue.cqp;

import com.rights.delay.common.exception.ConsumeQueueException;
import com.rights.delay.domain.JobMessage;
import com.rights.delay.service.queue.core.RealTimeQueueProvider;
import com.rights.delay.common.util.JsonUtil;
import com.rights.delay.service.queue.message.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Real time queue consumer
 *
 * @author 拓仲 on 2020/3/11
 */
public class RealTimeQueueConsumer implements RealTimeQueueProvider {

    private MessageProducer messageProducer;

    @Override
    public void sendMessage(JobMessage job) throws ConsumeQueueException {
        System.out.println(String.format("invoke topic %s json:%s", job.getTopic(), JsonUtil.convertObjectToJSON(job)));
        String topic = job.getTopic();
        String body = job.getBody();
        messageProducer.sendMessage(topic, body);
    }

    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }
}
