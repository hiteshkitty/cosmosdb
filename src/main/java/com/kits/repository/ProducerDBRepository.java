package com.kits.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.kits.model.Producer;

@Repository
public interface ProducerDBRepository extends CosmosRepository<Producer, String> {
	// Query for all documents
	@Query(value = "SELECT * FROM c")
	List<Producer> getAllProducers();

	Producer findByTopicName(String topicName);

	Long countByTopicName(String topicName);

	Producer findByCorrelationId(String correlationId);

	@Query("select * from c offset @offset limit @limit")
	List<Producer> getProducersWithOffsetLimit(@Param("offset") int offset, @Param("limit") int limit);

	@Query("select value count(1) from c where c.topicName = @topicName")
	long getNumberOfProducersWithTopicName(@Param("topicName") String topicName);

	Producer findByCorrelationIdAndTopicName(String correlationId, String topicName);

	@Query("SELECT DISTINCT VALUE prod.topicName	FROM producers prod")
	List<String> findAllDistinctTopicNames();

	@Query("select value count(1) from c where c.timeStamp > (GetCurrentTimestamp() - @timeValue)")
	int getProdcuerCountForDuration(long timeValue);
}
