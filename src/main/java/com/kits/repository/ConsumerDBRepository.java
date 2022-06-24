package com.kits.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.kits.cosmos.dto.MessageCountResponse;
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

}
