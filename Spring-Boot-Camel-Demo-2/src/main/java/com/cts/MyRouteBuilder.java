package com.cts;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRouteBuilder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("file:E:/source")
			.process(new FileProcessor())
			.to("file:E:/destination");
	}

}
