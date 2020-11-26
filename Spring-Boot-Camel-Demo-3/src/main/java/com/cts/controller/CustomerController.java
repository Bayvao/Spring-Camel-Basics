package com.cts.controller;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.Customer;

@RestController
public class CustomerController {

	@Autowired
	private ProducerTemplate producer;

	@GetMapping("/employees")
	public List<Customer> getCustomers() {
		List<Customer> customers = producer.requestBody("direct:select", null, List.class);
		return customers;
	}

	@PostMapping("/employees")
	public boolean insertCustomers(@RequestBody Customer customer) {
		
		Boolean response = producer.requestBody("direct:insert", customer, Boolean.class);
		return response;
	}
}
