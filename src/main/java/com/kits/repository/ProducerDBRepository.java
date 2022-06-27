package com.kits.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.kits.cosmos.dto.MessageCountResponse;
import com.kits.cosmos.dto.MessageProcessed;
import com.kits.model.Producer;

@Repository
public interface ProducerDBRepository extends CosmosRepository<Producer, String> {
	// Query for all documents
	@Query(value = "SELECT * FROM c")
	List<Producer> getAllProducers();

	@Query("SELECT DISTINCT VALUE prod.topicName	FROM producers prod")
	List<String> findAllDistinctTopicNames();

	@Query("select p.partitionNumber, min(p[\"offset\"]) as minOffset, max(p[\"offset\"]) as maxOffset, count(1) messages   \n"
			+ "from   myproducers p  \n" + "where  p.topicName = @topicName  and  \n"
			+ "p.processingOrder = @processingOrder and   \n" + "p.timeStamp >=  @startTime and  \n"
			+ "p.timeStamp <= @endTime    \n" + "group by p.partitionNumber ")
	List<MessageCountResponse> getAllMessageCountWithTimeAppId(String topicName, String processingOrder, Long startTime,
			Long endTime);

	@Query("select p[\"offset\"], p.partitionNumber from producer p\n"
			+ "where p.topicName = @topicName\n"
			+ "and p.processingOrder = \"MAIN\"\n"
			+ "and p.timeStamp >= @startTime\n"
			+ "and p.timeStamp <= @endTime order by p.partitionNumber")
	List<MessageProcessed> getAllOffsetProcessed(String topicName, long startTime, long endTime);
}
