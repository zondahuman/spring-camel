package com.abin.lee.camel.message.service.producer.topic;

import com.abin.lee.camel.message.common.converter.FeedMessageConverter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.Serializable;

@Service
public class CamelTopicMessageProducer {

	@Resource
	JmsTemplate jmsTemplateTopic;
	@Resource
	Destination camelsTopicDestination;
	@Resource
    FeedMessageConverter feedMessageConverter;
    @Resource
    ActiveMQConnectionFactory amqConnectionFactory;


	public void sendTopic1Message(final Serializable obj) {
		// jmsTemplate.send(destination, new MessageCreator() {
		//
		// public Message createMessage(Session session) throws JMSException {
		// ObjectMessage objMessage = session.createObjectMessage(obj);
		// return objMessage;
		// }
		//
		// });

		// 使用MessageConverter的情况
        jmsTemplateTopic.setMessageConverter(this.feedMessageConverter);
		// 使用MessageConverter的情况
        jmsTemplateTopic.convertAndSend(camelsTopicDestination, obj);
	}
	
	public void sendCamelTopicMessage(String message) {
		// 使用MessageConverter的情况
//		jmsTemplateTopic.setMessageConverter(this.feedMessageConverter);
		// 使用MessageConverter的情况
        jmsTemplateTopic.convertAndSend(camelsTopicDestination, message);
//        jmsTemplateTopic.convertAndSend(message);
	}

    public void sendNative(String messages) throws JMSException {
            Connection connection = amqConnectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("VirtualTopic.CAMEL");
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage();
            message.setText(messages);
            producer.send(message);
            System.out.println("Sent message: " + message.getText());
           session.close();
           connection.stop();
           connection.close();
    }

}
