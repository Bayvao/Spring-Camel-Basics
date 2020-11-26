package com.cts.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.cts.model.Customer;

@Service
public class EmployeeService extends RouteBuilder {

	@Autowired
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	public SqlComponent getSqlComponent() {
		
		SqlComponent sql = new SqlComponent();
		sql.setDataSource(dataSource);
		
		return sql;
	}

	@Override
	public void configure() throws Exception {
		//insert route
		from("direct:insert").log("Message is being processed...!").setHeader("message", body()).process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				Customer customer = exchange.getIn().getBody(Customer.class);
				
				Map<String, Object> customerMap = new HashMap<>();
				
				customerMap.put("id", customer.getId());
				customerMap.put("name", customer.getName());
				
				exchange.getIn().setBody(customerMap);
			}
			
		})
		.to("sql:INSERT INTO customer VALUES(:#id, :#name)");
		
		
		//select route
		from("direct:select").to("sql:select * from customer").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				List<Map<String, String>> dataList =  (List<Map<String, String>>) exchange.getIn().getBody();
				
				List<Customer> customers = new ArrayList<>();
				
				for(Map<String, String> data : dataList) {
					Customer customer = new Customer();
					
					customer.setId(data.get("id"));
					customer.setName(data.get("name"));
					
					customers.add(customer);
				}
				
				exchange.getIn().setBody(customers);
			}
			
		});
	}
	

}
