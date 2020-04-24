package com.rights.delay.service.queue.message;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author 拓仲 on 2020/3/29
 */
@Component
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);



    @JmsListener(destination = "test.queue",containerFactory = "jmsListenerQueueContainer")
    public void receiveQueue(String text) {
        logger.info("Consumer收到的报文为:" + text);
    }
}