package com.kits.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.kits.model.Producer;

@Repository
public interface ProducerDBRepository extends CosmosRepository<Producer, String> {
	// Query for all documents
	@Query(value = "SELECT * FROM c")
	List<Producer> getAllProducers();

}
