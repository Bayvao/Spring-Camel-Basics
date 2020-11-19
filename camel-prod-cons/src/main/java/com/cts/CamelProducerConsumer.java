package com.cts;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/*
 *  A Producer will produce some data and it will send to direct endpoint
 *  From direct endpoint, seda component  with consume automatically
 *  And from seda component, our consumer will consume the data finally
 */

public class CamelProducerConsumer {

	public static void main(String[] args) throws Exception {
		
		CamelContext camel = new DefaultCamelContext();
		
		camel.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("direct:start").to("seda:end");
			}
		});
		
		camel.start();
		
		ProducerTemplate producer = camel.createProducerTemplate();
		
		producer.sendBody("direct:start", "Hello from Camel Producer");
		
		ConsumerTemplate consumer = camel.createConsumerTemplate();
		
		String message = consumer.receiveBody("seda:end", String.class);
		
		System.out.println(message);
	}
}
