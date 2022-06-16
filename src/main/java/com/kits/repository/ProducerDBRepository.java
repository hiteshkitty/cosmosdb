package com.kits.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.kits.dto.MessageCountResponse;
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

	@Query("select p.partitionNumber, min(p[\"offset\"]) as minOffset, max(p[\"offset\"]) as maxOffset, count(1) messages  \n"
			+ "from   myproducers p \n"
			+ "where  p.topicName = @topicName  and \n"
			+ "              p.processingOrder = @processingOrder \n"
			+ "group by p.partitionNumber")
	List<MessageCountResponse> getAllMessageCount(String topicName, String processingOrder);
	
	@Query("select p.partitionNumber, min(p[\"offset\"]) as minOffset, max(p[\"offset\"]) as maxOffset, count(1) messages  \n"
			+ "from   myproducers p \n"
			+ "where  p.topicName = @topicName  and \n"
			+ "              p.processingOrder = @processingOrder  and p.timeStamp > (GetCurrentTimestamp() - @timeValue) \n"
			+ "group by p.partitionNumber")
	List<MessageCountResponse> getAllMessageCountByTime(String topicName, String processingOrder, int timeValue);

	@Query("select p.partitionNumber, min(p[\"offset\"]) as minOffset, max(p[\"offset\"]) as maxOffset, count(1) messages   \n"
			+ "from   myproducers p  \n"
			+ "where  p.topicName = @topicName  and  \n"
			+ "p.processingOrder = @processingOrder and   \n"
			+ "p.timeStamp >=  @startTime and  \n"
			+ "p.timeStamp <= @endTime    \n"
			+ "group by p.partitionNumber ")
	List<MessageCountResponse> getAllMessageCountWithTime(String topicName, String processingOrder, Long startTime,
			Long endTime);
	
	@Query("select p.partitionNumber, min(p[\"offset\"]) as minOffset, max(p[\"offset\"]) as maxOffset, count(1) messages   \n"
			+ "from   myproducers p  \n"
			+ "where  p.topicName = @topicName  and  \n"
			+ "p.processingOrder = @processingOrder and   \n"
			+ "p.timeStamp >=  @startTime and  \n"
			+ "p.timeStamp <= @endTime    \n"
			+ "group by p.partitionNumber ")
	List<MessageCountResponse> getAllMessageCountWithTimeAppId(String topicName, String processingOrder, Long startTime,
			Long endTime);
}
