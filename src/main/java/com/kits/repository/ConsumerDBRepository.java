package com.kits.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.kits.cosmos.dto.MessageCountResponse;
import com.kits.cosmos.dto.MessageProcessed;
import com.kits.cosmos.dto.PendingTopicDetails;
import com.kits.model.Consumer;

@Repository
public interface ConsumerDBRepository extends CosmosRepository<Consumer, String> {

	@Query("select consumer.consumerAppId,  count(1) messages  \n" + "from   consumers consumer \n"
			+ "where  consumer.topicName = @topicName  and \n"
			+ "              consumer.processingOrder = @processingOrder and\n"
			+ "              consumer.partitionNumber = @partitionNumber and consumer[\"offset\"] >=  @minOffset and consumer[\"offset\"] <= @maxOffset\n"
			+ "group by consumer.consumerAppId\n")
	List<MessageCountResponse> getAllMessageCountByTimeWithOffsetAppId(String topicName, String processingOrder,
			int partitionNumber, int minOffset, int maxOffset);

	@Query("SELECT c.consumerAppId, c.topicName, c.partitionNumber, c.processingOrder, c[\"offset\"] FROM c where c.topicName =  @topicName and c.processingOrder = @processingOrder\n"
			+ "and (c[\"offset\"] > @minOffset and c[\"offset\"] < @maxOffset)")
	List<PendingTopicDetails> getPendingTopicDetails(String topicName, String processingOrder, int minOffset,
			int maxOffset);

	@Query("select c[\"offset\"], c.partitionNumber from myconsumers c\n"
			+ "where c.topicName = @topicName\n"
			+ "and c.processingOrder in (\"dead-letter\", \"MAIN\")\n"
			+ "and c.partitionNumber = @partitionNumber \n"
			+ "and c.consumerAppId = @consumerTopic \n"
			+ "and c[\"offset\"] >=  @minOffset  \n"
			+ "and c[\"offset\"] <= @maxOffset \n"
			+ " order by c.partitionNumber")
	List<MessageProcessed> getAllOffsetProcessed(String topicName, String consumerTopic, int partitionNumber, int minOffset, int maxOffset);

}
