package com.cts;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMqConsumer {
	public static void main(String[] args) throws Exception {
		CamelContext camel = new DefaultCamelContext();

		ConnectionFactory factory = new ActiveMQConnectionFactory();

		camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));

		camel.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("activemq:queue:my_queue").process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						String message = exchange.getIn().getBody(String.class);

						exchange.getOut().setBody(message.toUpperCase());
					}
				}).to("seda:end");
			}

		});

		while (true) {
			camel.start();

			ConsumerTemplate consumer = camel.createConsumerTemplate();
			String message = consumer.receiveBody("seda:end", String.class);

			System.out.println(message);
		}
	}
}
