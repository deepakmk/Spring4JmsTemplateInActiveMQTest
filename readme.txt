           This is a Stand alone for testing Spring JMS with ActiveMQ
  
1. Sending and Receiving Messages without JmsTemplate
    JmsProducer.java
    JmsConsumer.java

2. Use JmsTemplate:    
2.1. Configuring JmsTemplate(applicationContext.xml):
 <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
		<property name="brokerURL" value="tcp://localhost:61616" />
	</bean>
	<bean id="messageDestination" class="org.apache.activemq.command.ActiveMQQueue">
		<constructor-arg value="messageQueue1" />
	</bean>
	<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
		<property name="connectionFactory" ref="connectionFactory" />
		<property name="receiveTimeout" value="10000" />
	</bean>

	<bean id="springJmsProducer" class="com.javacodegeeks.spring.jms.SpringJmsProducer">
		<property name="destination" ref="messageDestination" />
		<property name="jmsTemplate" ref="jmsTemplate" />
	</bean>

	<bean id="springJmsConsumer" class="com.javacodegeeks.spring.jms.SpringJmsConsumer">
		<property name="destination" ref="messageDestination" />
		<property name="jmsTemplate" ref="jmsTemplate" />
	</bean>
  </beans>

2.2. SpringJmsProducer.java/SpringJmsConsumer.java


3. Complete JmsTemplate example to send/receive messages
   SpringJmsTemplateExample.java
   
   
4.1. JmsTemplate with Default destination:
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
		<property name="brokerURL" value="tcp://localhost:61616" />
	</bean>
	<bean id="messageDestination" class="org.apache.activemq.command.ActiveMQQueue">
		<constructor-arg value="messageQueue1" />
	</bean>
	<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
		<property name="connectionFactory" ref="connectionFactory" />
		<property name="receiveTimeout" value="10000" />
		<property name="defaultDestination" ref="messageDestination" />
	</bean>

	<bean id="springJmsProducer" class="com.javacodegeeks.spring.jms.SpringJmsProducer">
		<property name="jmsTemplate" ref="jmsTemplate" />
	</bean>
	
	<bean id="springJmsConsumer" class="com.javacodegeeks.spring.jms.SpringJmsConsumer">
		<property name="jmsTemplate" ref="jmsTemplate" />
	</bean>	

</beans>

4.2. SpringJmsProducer.java/SpringJmsConsumer.java
     SpringJmsTemplateDefaultDestinExample.java
  

5. JmsTemplate with MessageConverter
   public interface MessageConverter {
      public Message toMessage(Object object, Session session);
      public Object fromMessage(Message message);
   }
   
   PersonMessageConverter.java:
public class PersonMessageConverter implements MessageConverter{

	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {		
		Person person = (Person) object;
		MapMessage message = session.createMapMessage();
		message.setString("name", person.getName());
		message.setInt("age", person.getAge());
		return message;
	}

	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		MapMessage mapMessage = (MapMessage) message;
		Person person = new Person(mapMessage.getString("name"), mapMessage.getInt("age"));
		return person;
	}

}
<bean id="personMessageConverter" class="com.javacodegeeks.spring.jms.PersonMessageConverter" />


5.2. SpringJmsMessageConverterExample.java

 
   
   
   
   

