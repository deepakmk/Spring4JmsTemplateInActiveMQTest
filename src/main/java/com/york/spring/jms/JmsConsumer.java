package com.york.spring.jms;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer {
	static String brokerURL="tcp://localhost:61616";
	static String queueName="customerQueue";
	
	public static void main(String[] args) throws URISyntaxException, Exception {
		Connection connection = null;
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,
				Session.CLIENT_ACKNOWLEDGE);
		try {
			Queue queue = session.createQueue(queueName);

			// Consumer
			MessageConsumer consumer = session.createConsumer(queue);
			TextMessage textMsg = (TextMessage) consumer.receive();
			
			System.out.println(textMsg);
			System.out.println("Received: " + textMsg.getText());	
			textMsg.acknowledge();
		}catch(Exception e) {
			System.out.println("exception");
			e.printStackTrace();
		} finally {
			
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
