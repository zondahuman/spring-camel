package com.abin.lee.camel.message.common.monitor;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JMXService {
	// private static final String serviceUrl = "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi";
	private static final String serviceUrl = "service:jmx:rmi:///jndi/rmi://172.30.0.21:1099/jmxrmi";
	private static final String brokerViewName = "org.apache.activemq:type=Broker,brokerName=test";
	private static final String queueViewName = "org.apache.activemq:type=Broker,brokerName=test,destinationType=Queue,destinationName=test.queue4";

	// private static final String queueViewName = "org.apache.activemq:type=Broker,brokerName=test,destinationType=Queue,destinationName=test.queue3";

	public static void main(String[] args) throws Exception {
		// createMessage(serviceUrl, queueViewName, "abin");
		String messageId = "ID:lee-59799-1435128507041-1:1:14:1:1";
		String destinationName = "test.queue4";
		// String messageId= "ID:lx-dev00.lx.betafang.com-54650-1434705261395-102:1:1:1:1";
		// removeMessage(serviceUrl, queueViewName, messageId);
		// retryMessage(serviceUrl, queueViewName, messageId);
		// moveMessageTo(serviceUrl, queueViewName, messageId, destinationName);
//		 String key = "Text";
//		 Object object = findMessageByMessageId(serviceUrl, queueViewName,
//		 messageId, key);
//		 System.out.println("object="+object);
		List list = browseMessages(serviceUrl, queueViewName, messageId);
		System.out.println("list=" + list);
//		TabularData tabularData = browseMessage(serviceUrl, queueViewName);
//		System.out.println("tabularData=" + tabularData);
	}

	public static MBeanServerConnection createMBeanConnection(String serviceUrl)
			throws IOException {
		JMXServiceURL url = new JMXServiceURL(serviceUrl);
		JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
		MBeanServerConnection conn = jmxConnector.getMBeanServerConnection();
		return conn;
	}

	public static BrokerViewMBean createBrokerViewMBean(String serviceUrl,
			String brokerViewName) throws MalformedObjectNameException,
			IOException {
		MBeanServerConnection conn = createMBeanConnection(serviceUrl);
		ObjectName activeMQ = new ObjectName(brokerViewName);
		BrokerViewMBean mbean = (BrokerViewMBean) MBeanServerInvocationHandler
				.newProxyInstance(conn, activeMQ, BrokerViewMBean.class, true);
		return mbean;
	}

	public static QueueViewMBean createQueueViewMBean(String serviceUrl,
			String queueViewName) throws MalformedObjectNameException,
			IOException {
		MBeanServerConnection conn = createMBeanConnection(serviceUrl);
		ObjectName activeMQ = new ObjectName(queueViewName);
		QueueViewMBean mbean = (QueueViewMBean) MBeanServerInvocationHandler
				.newProxyInstance(conn, activeMQ, QueueViewMBean.class, true);
		return mbean;
	}
	
	public static DestinationViewMBean createDestinationViewMBean(String serviceUrl,
			String queueViewName) throws MalformedObjectNameException,
			IOException {
		MBeanServerConnection conn = createMBeanConnection(serviceUrl);
		ObjectName activeMQ = new ObjectName(queueViewName);
		DestinationViewMBean mbean = (DestinationViewMBean) MBeanServerInvocationHandler
				.newProxyInstance(conn, activeMQ, DestinationViewMBean.class, true);
		return mbean;
	}
	

	public static TopicViewMBean createTopicViewMBean(String serviceUrl,
			String queueViewName) throws MalformedObjectNameException,
			IOException {
		MBeanServerConnection conn = createMBeanConnection(serviceUrl);
		ObjectName activeMQ = new ObjectName(queueViewName);
		TopicViewMBean mbean = (TopicViewMBean) MBeanServerInvocationHandler
				.newProxyInstance(conn, activeMQ, TopicViewMBean.class, true);
		return mbean;
	}

	public static void createMessage(String serviceUrl, String queueViewName,
			String content) throws Exception {
		QueueViewMBean mbean = createQueueViewMBean(serviceUrl, queueViewName);
		mbean.sendTextMessage(content);
	}

	public static void removeMessage(String serviceUrl, String queueViewName,
			String messageId) throws Exception {
		QueueViewMBean mbean = createQueueViewMBean(serviceUrl, queueViewName);
		mbean.removeMessage(messageId);
	}

	public static void retryMessage(String serviceUrl, String queueViewName,
			String messageId) throws Exception {
		QueueViewMBean mbean = createQueueViewMBean(serviceUrl, queueViewName);
		mbean.retryMessage(messageId);
	}

	public static void moveMessageTo(String serviceUrl, String queueViewName,
			String messageId, String destinationName) throws Exception {
		QueueViewMBean mbean = createQueueViewMBean(serviceUrl, queueViewName);
		mbean.moveMessageTo(messageId, destinationName);
	}

	public static Object findMessageByMessageId(String serviceUrl,
			String queueViewName, String messageId, String key)
			throws Exception {
		QueueViewMBean mbean = createQueueViewMBean(serviceUrl, queueViewName);
		CompositeData compositeData = mbean.getMessage(messageId);
		Object object = null;
		if (null != compositeData)
			object = compositeData.get(key);
		return object;
	}

	public static List<CompositeData> browseMessages(String serviceUrl,
			String queueViewName, String messageId) throws Exception {
		QueueViewMBean queueMbean = createQueueViewMBean(serviceUrl, queueViewName);
		CompositeData[] compositeDatas = queueMbean.browse();
		System.out.println(compositeDatas);
		List<CompositeData> list = Arrays.asList(compositeDatas);
		return list;
	}

	public static TabularData browseMessage(String serviceUrl,
			String queueViewName) throws Exception {
		DestinationViewMBean queueMbean = createDestinationViewMBean(serviceUrl, queueViewName);
//		List<Message> list1= (List<Message>) queueMbean.browseMessages();
//		System.out.println(list1);
		TabularData tabularData = queueMbean.browseAsTable();
//		System.out.println(tabularData);
//		Collection list = tabularData.values();
//		System.out.println(list);
		return tabularData;
	}
	
	
	
	
	
	

}
