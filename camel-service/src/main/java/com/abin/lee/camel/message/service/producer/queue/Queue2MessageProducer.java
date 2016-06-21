package com.abin.lee.camel.message.service.producer.queue;

import com.abin.lee.camel.message.common.converter.FeedMessageConverter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.io.Serializable;


@Service
public class Queue2MessageProducer {

	@Resource
	JmsTemplate jmsTemplateQueue;
	@Resource
	Destination queue2Subjects;
	@Resource
    FeedMessageConverter feedMessageConverter;

	public void sendQueue2Message(final Serializable obj) {
		// jmsTemplate.send(destination, new MessageCreator() {
		//
		// public Message createMessage(Session session) throws JMSException {
		// ObjectMessage objMessage = session.createObjectMessage(obj);
		// return objMessage;
		// }
		//
		// });

		// 使用MessageConverter的情况
		jmsTemplateQueue.setMessageConverter(this.feedMessageConverter);
		// 使用MessageConverter的情况
		jmsTemplateQueue.convertAndSend(queue2Subjects, obj);
	}
	
	public void sendQueue2Message(String message) {
		// 使用MessageConverter的情况
		jmsTemplateQueue.convertAndSend(queue2Subjects, message);
	}
}
