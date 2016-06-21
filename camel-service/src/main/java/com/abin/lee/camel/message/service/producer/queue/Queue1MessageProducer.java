package com.abin.lee.camel.message.service.producer.queue;

import com.abin.lee.camel.message.common.converter.FeedMessageConverter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.io.Serializable;

@Service
public class Queue1MessageProducer {

	@Resource
	JmsTemplate jmsTemplateQueue;
	@Resource
	Destination queue1Subjects;
	@Resource
    FeedMessageConverter feedMessageConverter;

	public void sendQueue1Message(final Serializable obj) {
		// jmsTemplate.send(destination, new MessageCreator() {
		//
		// public Message createMessage(Session session) throws JMSException {
		// ObjectMessage objMessage = session.createObjectMessage(obj);
		// return objMessage;
		// }
		//
		// });

		jmsTemplateQueue.setMessageConverter(this.feedMessageConverter);
		// 使用MessageConverter的情况
		jmsTemplateQueue.convertAndSend(queue1Subjects, obj);
	}
	
	public void sendQueue1Message(String message) {
		// jmsTemplate.send(destination, new MessageCreator() {
		//
		// public Message createMessage(Session session) throws JMSException {
		// ObjectMessage objMessage = session.createObjectMessage(obj);
		// return objMessage;
		// }
		//
		// });

		// 使用MessageConverter的情况
		jmsTemplateQueue.convertAndSend(queue1Subjects, message);
	}


}
