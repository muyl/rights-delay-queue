package com.rights.delay.service.queue.cqp;

import com.rights.delay.common.exception.ConsumeQueueException;
import com.rights.delay.domain.JobMessage;
import com.rights.delay.service.queue.core.RealTimeQueueProvider;
import com.rights.delay.common.util.JsonUtil;
import com.rights.delay.service.queue.message.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Real time queue consumer
 *
 * @author 拓仲 on 2020/3/11
 */
public class RealTimeQueueConsumer implements RealTimeQueueProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealTimeQueueConsumer.class);

    private MessageProducer messageProducer;

    @Override
    public void sendMessage(JobMessage job) throws ConsumeQueueException {
        String topic = job.getTopic();
        String body = job.getBody();
        messageProducer.sendMessage(topic, body);
    }

    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }
}
