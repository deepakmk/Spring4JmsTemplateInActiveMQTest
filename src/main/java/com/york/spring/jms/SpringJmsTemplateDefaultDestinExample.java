package com.york.spring.jms;

import java.net.URISyntaxException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringJmsTemplateDefaultDestinExample {
	public static void main(String[] args) throws URISyntaxException, Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"appContextWithMessageConverter.xml");

		try {
			SpringJmsProducer springJmsProducer = (SpringJmsProducer) context
					.getBean("springJmsProducer");
			springJmsProducer.sendMessage("SomeTask");

			SpringJmsConsumer springJmsConsumer = (SpringJmsConsumer) context
					.getBean("springJmsConsumer");
			System.out.println("Consumer receives " + springJmsConsumer.receiveMessage());
		} finally {
			context.close();
		}
	}
}
