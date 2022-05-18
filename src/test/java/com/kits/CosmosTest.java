//package com.kits;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//import java.util.Collections;
//
//import org.junit.Test;
//import org.junit.jupiter.api.Order;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.kits.dto.CustomerResponse;
//import com.kits.model.Address;
//import com.kits.model.Customer;
//
//import lombok.extern.slf4j.Slf4j;
//
//@EnableAutoConfiguration
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class CosmosTest {
//
//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//
//	private static String customerID;
//
//	private Customer c;
//
//	@Test
//	@Order(1)
//	public void testCreateCustomer() {
//		// test to create a new customer in cosmosdb
//		Customer c = new Customer("Prateek", "Kumar", "9087654321", null);
//		CustomerResponse postResponse = this.testRestTemplate
//				.postForObject("http://localhost:" + port + "/api/v1/customers", c, CustomerResponse.class);
//		int idPos = postResponse.getMessage().indexOf(":") + 2;
//		customerID = postResponse.getMessage().substring(idPos);
//		System.out.println("ID of the newly created customer is " + customerID);
//		assertThat(postResponse.getStatusCode()).isEqualTo("00");
//
//	}
//
//	@Test
//	@Order(2)
//	public void testUpdateCustomerAddress() {
//		// Test to update an existing customer
//		System.out.println("Trying to fetch the customer details for the customer id: " + customerID);
//		Customer[] customerList = this.testRestTemplate
//				.getForObject("http://localhost:" + port + "/api/v1/customers/" + customerID, Customer[].class);
//		assertThat(customerList.length).isEqualTo(1);
//		Customer retrievedCustomer = customerList[0];
//		assertThat(retrievedCustomer.getFirstName()).isEqualTo("Prateek");
//		assertThat(retrievedCustomer.getAddresses()).isNull();
//
//		// Update the customer address
//		System.out.println("Update the customer address");
//		Address customerAddress = new Address();
//		customerAddress.setAddressLine1("address line 1");
//		customerAddress.setAddressLine2("address line 2");
//		customerAddress.setAddressLine3("address line 3");
//		customerAddress.setIsCurrentAddress("true");
//		customerAddress.setIsPermanentAddress("true");
//		customerAddress.setCity("city");
//		customerAddress.setPinCode("123456");
//		customerAddress.setState("state");
//
//		retrievedCustomer.setAddresses(Collections.singletonList(customerAddress));
//
//		System.out.println("Updating the details for the customer id: " + customerID);
//		this.testRestTemplate.put("http://localhost:" + port + "/api/v1/customers/" + customerID, retrievedCustomer);
//		customerList = this.testRestTemplate
//				.getForObject("http://localhost:" + port + "/api/v1/customers/" + customerID, Customer[].class);
//		assertThat(customerList.length).isEqualTo(1);
//		retrievedCustomer = customerList[0];
//		assertThat(retrievedCustomer.getFirstName()).isEqualTo("Prateek");
//		assertThat(retrievedCustomer.getAddresses()).isNotNull();
//		assertThat(retrievedCustomer.getAddresses().size()).isEqualTo(1);
//
//	}
//
//	@Test
//	@Order(3)
//	public void testDeleteCustomer() {
//		System.out.println("Deleting the details for the customer id: " + customerID);
//		this.testRestTemplate.delete("http://localhost:" + port + "/api/v1/customers/" + customerID);
//
//		System.out.println("Trying to fetch the customer details for the customer id: " + customerID);
//		Customer[] customers = this.testRestTemplate
//				.getForObject("http://localhost:" + port + "/api/v1/customers/" + customerID, Customer[].class);
//		assertThat(customers.length).isEqualTo(0);
//
//	}
//
//	@Test
//	@Order(4)
//	public void testGetAllCustomers() {
//		Customer c = new Customer("Prateek1", "Kumar", "9087654321", null);
//		this.testRestTemplate.postForObject("http://localhost:" + port + "/api/v1/customers", c,
//				CustomerResponse.class);
//		c = new Customer("Prateek2", "Kumar", "9087654321", null);
//		this.testRestTemplate.postForObject("http://localhost:" + port + "/api/v1/customers", c,
//				CustomerResponse.class);
//		c = new Customer("Prateek3", "Kumar", "9087654321", null);
//		this.testRestTemplate.postForObject("http://localhost:" + port + "/api/v1/customers", c,
//				CustomerResponse.class);
//
//		Customer[] customerList = this.testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/customers",
//				Customer[].class);
//		assertThat(customerList.length).isEqualTo(3);
//
//	}
//}
