package com.cts;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMqToCamel {

	public static void main(String[] args) throws Exception {
		CamelContext camel = new DefaultCamelContext();

		ConnectionFactory factory = new ActiveMQConnectionFactory();

		camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));

		camel.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("activemq:queue:my_queue").to("stream:out");
			}

		});
		
		camel.start();
		
		Thread.sleep(1000);
		
		camel.stop();
	}
}
