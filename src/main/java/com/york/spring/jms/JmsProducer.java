package com.york.spring.jms;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProducer {
	static String brokerURL="tcp://localhost:61616";
	static String queueName="customerQueue";
	
	public static void main(String[] args) throws URISyntaxException, Exception {
		Connection connection = null;
		try {
			// Producer
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					brokerURL);
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);
			Queue queue = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(queue);
			String payload = "SomeTask";
			Message msg = session.createTextMessage(payload);
			System.out.println("Sending text '" + payload + "'");
			producer.send(msg);
			session.close();
		}catch(final Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}
