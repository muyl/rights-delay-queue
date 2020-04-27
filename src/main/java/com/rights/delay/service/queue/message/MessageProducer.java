package com.rights.delay.service.queue.message;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;


/**
 * Message producer
 *
 * @author 拓仲 on 2020/3/29
 */
@Service("messageProducer")
public class MessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    // 发送消息，destination是发送到的队列，message是待发送的消息

    private void sendMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送消息
     *
     * @param queueName queue name
     * @param message message
     */
    public void sendMessage(String queueName, String message) {
        Destination destination = new ActiveMQQueue(queueName);
        sendMessage(destination, message);
        logger.info("发送消息：{}", message);
    }
}