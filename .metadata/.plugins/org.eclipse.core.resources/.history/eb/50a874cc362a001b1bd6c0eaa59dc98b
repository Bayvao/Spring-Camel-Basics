package com.cts;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelHelloWorld {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		CamelContext camel = new DefaultCamelContext();
		
		camel.addRoutes(new MyRouteBuilder());

		camel.start();
		
		Thread.sleep(2000);
		
		camel.close();
	}

}
