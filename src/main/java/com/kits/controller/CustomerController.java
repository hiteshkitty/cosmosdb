package com.kits.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.cosmos.models.PartitionKey;
import com.kits.dto.CustomerResponse;
import com.kits.model.Customer;
import com.kits.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

   @Autowired
   private CustomerService customerService;


    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    //Add new Customer
    @PostMapping
    public ResponseEntity<CustomerResponse> createNewCustomer(@RequestBody Customer c) {

        c = customerService.save(c);
        CustomerResponse customerCrudResponse = new CustomerResponse();
        customerCrudResponse.setMessage("New customer created successfully with the ID: " + c.getId());
        customerCrudResponse.setStatusCode("00");
        return new ResponseEntity<CustomerResponse>(customerCrudResponse, HttpStatus.CREATED);
    }

    //Update existing Customer
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExistingCustomer(@PathVariable String id, @RequestBody Customer c) {

        customerService.save(c);
        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }


    //get customer details
    @GetMapping("/{id}")
    public ResponseEntity<List<Customer>> getCustomers(@PathVariable String id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("ContentType", "application/json");
        List<Customer> customerList = new ArrayList<>();

        logger.info("Id is present in the GET request");
        List<Optional<Customer>> optionaCustomerList = Collections.singletonList(customerService.findById(id));
        if (!(optionaCustomerList.get(0).isEmpty())) {
            optionaCustomerList.stream().forEach(c -> c.ifPresent(customer -> customerList.add(customer)));
            return new ResponseEntity<List<Customer>>(customerList, responseHeaders, HttpStatus.OK);
        }


        return new ResponseEntity<List<Customer>>(customerList, responseHeaders, HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("ContentType", "application/json");
        List<Customer> customerList = new ArrayList<>();

        logger.info("Id is not present in the GET request");
        customerList = customerService.getAllCustomers();
        return new ResponseEntity<List<Customer>>(customerList, responseHeaders, HttpStatus.OK);

    }

    //delete the customer of a particular id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExistingCustomer(@PathVariable String id) {
        Optional<Customer> customer = customerService.findById(id);
        customerService.deleteById(id, new PartitionKey(customer.get().getLastName()));
        return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
    }
}
