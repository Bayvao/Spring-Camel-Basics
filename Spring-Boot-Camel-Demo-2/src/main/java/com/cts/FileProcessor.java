package com.cts;

import java.util.StringTokenizer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class FileProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String message = exchange.getIn().getBody(String.class);
		
		StringTokenizer tokenizer = new StringTokenizer(message, ",");
		
		String eid = tokenizer.nextToken();
		String ename = tokenizer.nextToken();
		String esal = tokenizer.nextToken();
		
		String outMessage = "{eid:" + eid + ",ename:" + ename + ",esal:" + esal + "}";
		
		exchange.getIn().setBody(outMessage);
	}

}
