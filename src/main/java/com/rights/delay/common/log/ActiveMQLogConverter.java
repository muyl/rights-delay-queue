package com.rights.delay.common.log;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.util.ObjectUtils;

import javax.jms.*;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Active mq log converter
 *
 * @author 拓仲 on 2020/3/31
 */
public class ActiveMQLogConverter implements MessageConverter {

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (object instanceof Message) {
            return (Message) object;
        } else if (object instanceof String) {
            return this.createMessageForString((String) object, session);
        } else if (object instanceof byte[]) {
            return this.createMessageForByteArray((byte[]) ((byte[]) object), session);
        } else if (object instanceof Map) {
            return this.createMessageForMap((Map) object, session);
        } else if (object instanceof Serializable) {
            return this.createMessageForSerializable((Serializable) object, session);
        } else {
            throw new MessageConversionException("Cannot convert object of type [" + ObjectUtils.nullSafeClassName(object) + "] to JMS message. Supported message " + "payloads are: String, byte array, Map<String,?>, Serializable object.");
        }
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        return message instanceof TextMessage ? this.extractStringFromMessage((TextMessage) message) : (message instanceof BytesMessage ? this.extractByteArrayFromMessage((BytesMessage) message) : (message instanceof MapMessage ? this.extractMapFromMessage((MapMessage) message) : (message instanceof ObjectMessage ? this.extractSerializableFromMessage((ObjectMessage) message) : message)));
    }

    /**
     * Create message for string text message
     *
     * @param text text
     * @param session session
     * @return the text message
     * @throws JMSException jms exception
     */
    protected TextMessage createMessageForString(String text, Session session) throws JMSException {
        TextMessage textMessage = session.createTextMessage(text);
        textMessage.setStringProperty(RequestLog.PASSTHROUGH_LOG, RequestLog.getLogStr());
        return textMessage;
    }

    /**
     * Create message for byte array bytes message
     *
     * @param bytes bytes
     * @param session session
     * @return the bytes message
     * @throws JMSException jms exception
     */
    protected BytesMessage createMessageForByteArray(byte[] bytes, Session session) throws JMSException {
        BytesMessage message = session.createBytesMessage();
        message.writeBytes(bytes);
        message.setStringProperty(RequestLog.PASSTHROUGH_LOG, RequestLog.getLogStr());
        return message;
    }

    /**
     * Create message for map map message
     *
     * @param map map
     * @param session session
     * @return the map message
     * @throws JMSException jms exception
     */
    protected MapMessage createMessageForMap(Map<?, ?> map, Session session) throws JMSException {
        MapMessage message = session.createMapMessage();
        Iterator var4 = map.entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry) var4.next();
            if (!(entry.getKey() instanceof String)) {
                throw new MessageConversionException("Cannot convert non-String key of type [" + ObjectUtils.nullSafeClassName(entry.getKey()) + "] to JMS MapMessage entry");
            }
            message.setObject((String) entry.getKey(), entry.getValue());
        }
        message.setStringProperty(RequestLog.PASSTHROUGH_LOG, RequestLog.getLogStr());

        return message;
    }

    /**
     * Create message for serializable object message
     *
     * @param object object
     * @param session session
     * @return the object message
     * @throws JMSException jms exception
     */
    protected ObjectMessage createMessageForSerializable(Serializable object, Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage(object);
        objectMessage.setStringProperty(RequestLog.PASSTHROUGH_LOG, RequestLog.getLogStr());
        return objectMessage;
    }

    /**
     * Extract string from message string
     *
     * @param message message
     * @return the string
     * @throws JMSException jms exception
     */
    protected String extractStringFromMessage(TextMessage message) throws JMSException {

        String msg = message.getText();
        setMDCLog(message);

        return msg;
    }

    /**
     * Extract byte array from message byte [ ]
     *
     * @param message message
     * @return the byte [ ]
     * @throws JMSException jms exception
     */
    protected byte[] extractByteArrayFromMessage(BytesMessage message) throws JMSException {
        byte[] bytes = new byte[(int) message.getBodyLength()];
        message.readBytes(bytes);
        setMDCLog(message);
        return bytes;
    }

    /**
     * Extract map from message map
     *
     * @param message message
     * @return the map
     * @throws JMSException jms exception
     */
    protected Map<String, Object> extractMapFromMessage(MapMessage message) throws JMSException {
        Map<String, Object> map = new HashMap<>();
        Enumeration en = message.getMapNames();

        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            map.put(key, message.getObject(key));
        }

        setMDCLog(message);

        return map;
    }

    /**
     * Extract serializable from message serializable
     *
     * @param message message
     * @return the serializable
     * @throws JMSException jms exception
     */
    protected Serializable extractSerializableFromMessage(ObjectMessage message) throws JMSException {
        setMDCLog(message);
        return message.getObject();
    }

    private void setMDCLog(Message message) throws JMSException {
        String logStr = message.getStringProperty(RequestLog.PASSTHROUGH_LOG);
        RequestLog.setMDCLog(logStr);
    }
}
