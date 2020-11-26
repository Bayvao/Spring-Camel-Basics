package com.cts;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

public class CamelTimer {

	public static void main(String[] args) throws Exception {

		MyCustomBean myBean = new MyCustomBean();
		SimpleRegistry registry = new SimpleRegistry();
		registry.bind("customBean", MyCustomBean.class, myBean);

		CamelContext camel = new DefaultCamelContext(registry);

		camel.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				// configure the routes
				from("timer:myTimer?period=1000").setBody(simple("Hello From Camel at ${header.firedTime}"))
						.to("stream:out")
						.to("bean:customBean?method=display");
				// .bean(new MyCustomBean(), "display");

			}
		});

		while(true)
			camel.start();
		
	}
}
