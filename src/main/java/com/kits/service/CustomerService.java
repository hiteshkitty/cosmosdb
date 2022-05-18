package com.kits.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.azure.cosmos.models.PartitionKey;
import com.kits.model.Customer;

@Component
public class CustomerService {

//	@Autowired
//	CustomerDBRepository customerDBRepository;

	public Customer save(Customer c) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Customer> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteById(String id, PartitionKey partitionKey) {
		// TODO Auto-generated method stub

	}

}
