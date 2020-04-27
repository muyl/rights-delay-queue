package com.rights.delay.common.config;

import com.rights.delay.common.log.ActiveMQLogConverter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;

/**
 * Active mq config
 *
 * @author 拓仲 on 2020/3/29
 */
@Configuration
@EnableConfigurationProperties(ActiveMQProperties.class)
public class ActiveMQConfig {


    /**
     * Redelivery policy redelivery policy
     *
     * @return the redelivery policy
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy=   new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    /**
     * Connection factory connection factory
     *
     * @param properties properties
     * @param redeliveryPolicy redelivery policy
     * @return the connection factory
     */
    @Bean("connectionFactory")
    public ConnectionFactory connectionFactory(ActiveMQProperties properties, RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(
                        "admin",
                        "admin",
                        properties.getBrokerUrl());
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }


    /**
     * Active mq log converter message converter
     *
     * @return the message converter
     */
    @Bean("activeMQLogConverter")
    public MessageConverter activeMQLogConverter() {
        return new ActiveMQLogConverter();
    }


    /**
     * Jms template jms template
     *
     * @param activeMQLogConverter active mq log converter
     * @param connectionFactory connection factory
     * @return the jms template
     */
    @Bean("jmsTemplate")
    JmsTemplate jmsTemplate(MessageConverter activeMQLogConverter, ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        //进行持久化配置 1表示非持久化，2表示持久化
        jmsTemplate.setDeliveryMode(2);
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setMessageConverter(activeMQLogConverter);
        return jmsTemplate;
    }

    /**
     * Jms listener queue container jms listener container factory
     *
     * @param connectionFactory connection factory
     * @return the jms listener container factory
     */
    @Bean
    public JmsListenerContainerFactory jmsListenerQueueContainer(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置连接数
        factory.setConcurrency("1-10");
        factory.setPubSubDomain(Boolean.FALSE);
        factory.setMessageConverter(new ActiveMQLogConverter());
        return  factory;
    }

    /**
     * Jms listener topic container jms listener container factory
     *
     * @param connectionFactory connection factory
     * @return the jms listener container factory
     */
    @Bean
    public JmsListenerContainerFactory jmsListenerTopicContainer(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置连接数
        factory.setConcurrency("1-10");
        factory.setPubSubDomain(Boolean.TRUE);
        factory.setMessageConverter(new ActiveMQLogConverter());
        return  factory;
    }


}
