package com.abin.lee.camel.message.common.monitor;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

public class JmxMessage {

	public static void main(String[] args) throws IOException,
			MalformedObjectNameException, NullPointerException,
			InstanceNotFoundException, MBeanException, ReflectionException,
			OpenDataException {
		// TODO Auto-generated method stub

		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");

		JMXConnector connector = JMXConnectorFactory.connect(url, null);

		connector.connect();

		MBeanServerConnection connection = connector.getMBeanServerConnection();

		ObjectName name = new ObjectName(
				"org.apache.activemq:type=Broker,brokerName=test");

		BrokerViewMBean mbean = (BrokerViewMBean) MBeanServerInvocationHandler
				.newProxyInstance(connection, name, BrokerViewMBean.class, true);

		System.out.println("Statistics for broker " + mbean.getBrokerId()
				+ " - " + mbean.getBrokerName());

		System.out.println();

		System.out.println("Number of unacknowledged messages on the broker: "
				+ mbean.getTotalMessageCount());

		System.out
				.println("Number of messages that have been acknowledged on the broker: "
						+ mbean.getTotalDequeueCount());

		System.out
				.println("Number of messages that have been sent to the broker: "
						+ mbean.getTotalEnqueueCount());

		System.out
				.println("Number of message consumers subscribed to destinations on the broker: "
						+ mbean.getTotalConsumerCount());

		System.out.println("Total number of Queues: "
				+ mbean.getQueues().length);

		for (ObjectName queueName : mbean.getQueues())

		{

			QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(connection, queueName,
							QueueViewMBean.class, true);

			System.out.println();

			System.out.println("Statistics for queue " + queueMbean.getName());

			System.out
					.println("number of messages which are yet to be consumed: "
							+ queueMbean.getQueueSize());

			System.out
					.println("Number of consumers subscribed this destination: "
							+ queueMbean.getConsumerCount());

			CompositeData[] messages = queueMbean.browse();
			
			System.out.println();

			System.out.println("----------------------");

			for (int i = 0; i < messages.length; i++) {

				CompositeData data = messages[i];

				String value = "";

				if (data.containsKey("Text")) {

					value = (String) data.get("Text");

				} else if (data.containsKey("ContentMap")) {

					value = (String) data.get("ContentMap");

				}

				System.out.println(value);

			}

		}

	}

}
