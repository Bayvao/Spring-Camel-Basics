package com.cts;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CallMethodUsingClassComponent {

	public static void main(String[] args) throws Exception {
		
		CamelContext camelContext = new DefaultCamelContext();
		
		camelContext.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
					from("direct:abc")
						.to("class:com.cts.MyServiceClass?method=display");
			}
		});
		
		camelContext.start();
		
		ProducerTemplate prod = camelContext.createProducerTemplate();
		prod.sendBody("direct:abc", "Hello From Producer");
	}

}
