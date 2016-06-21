package com.abin.lee.camel.message.service.producer.topic;

import com.abin.lee.camel.message.common.converter.FeedMessageConverter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.io.Serializable;

@Service
public class Topic2MessageProducer {

	@Resource
	JmsTemplate jmsTemplateTopic;
	@Resource
	Destination topicDestination1;
	@Resource
    FeedMessageConverter feedMessageConverter;

	public void sendTopic2Message(final Serializable obj) {
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
		jmsTemplateTopic.convertAndSend(topicDestination1, obj);
	}
	
	public void sendTopic2Message(String message) {
		// 使用MessageConverter的情况
//		jmsTemplateTopic.setMessageConverter(this.feedMessageConverter);
		// 使用MessageConverter的情况
		jmsTemplateTopic.convertAndSend(topicDestination1, message);
	}
}
