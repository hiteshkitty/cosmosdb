package com.kits.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.kits.dto.MessageCountResponse;
import com.kits.model.Consumer;

@Repository
public interface ConsumerDBRepository extends CosmosRepository<Consumer, String> {
	// Query for all documents
	@Query(value = "SELECT * FROM c")
	List<Consumer> getAllConsumers();

	@Query("select c.partitionNumber, min(c[\"offset\"]) as minOffset, max(c[\"offset\"]) as maxOffset, count(1) messages  \n"
			+ "from   consumers c \n" + "where  c.topicName = @topicName  and \n"
			+ "              c.processingOrder = @processingOrder \n" + "group by c.partitionNumber")
	List<MessageCountResponse> getAllMessageCount(String topicName, String processingOrder);

	@Query("select c.partitionNumber, min(c[\"offset\"]) as minOffset, max(c[\"offset\"]) as maxOffset, count(1) messages  \n"
			+ "from   consumers c \n" + "where  c.topicName = @topicName  and \n"
			+ "              c.processingOrder = @processingOrder and c.timeStamp > (GetCurrentTimestamp() - @timeValue) \n" + "group by c.partitionNumber")
	List<MessageCountResponse> getAllMessageCountByTime(String topicName, String processingOrder, int timeValue);
	
	
	@Query("select consumer.partitionNumber, min(consumer[\"offset\"]) as minOffset, max(consumer[\"offset\"]) as maxOffset, count(1) messages  \n" +
            "from   consumers consumer \n" +
            "where  consumer.topicName = @topicName  and \n" +
            "              consumer.processingOrder = @processingOrder and\n" +
            "              consumer.partitionNumber = @partitionNumber and consumer[\"offset\"] >=  @minOffset and consumer[\"offset\"] <= @maxOffset\n" +
            "group by consumer.partitionNumber\n"
)
	List<MessageCountResponse> getAllMessageCountByTimeWithOffset(String topicName, String processingOrder, int partitionNumber, int minOffset, int maxOffset);
	
}
