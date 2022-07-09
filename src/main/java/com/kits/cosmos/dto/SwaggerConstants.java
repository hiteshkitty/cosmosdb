package com.kits.cosmos.dto;

public class SwaggerConstants {

	public static final String TITLE = "Optum APIs";

	public static final String MSG_AGGREGATE_TITLE = "APIs to fetch messages for topics for version V1";
	public static final String MSG_VERSION_1 = "V1";

	public static final String SUCCESS = "Success";
	public static final String BAD_REQUEST = "Bad Request";
	public static final String SERVER_ERROR = "Internal Server Error";
	public static final String UNAUTHORIZED_USER = "Unauthorized User";
	public static final String RESOURCE_NOT_FOUNT = "Resource Not found";
	
	public static final String GET_ALL_MISSED_MSG_SUMMARY = "Get all missed messages for a topic within certain duration";
	public static final String GET_ALL_MISSED_MSG_DESCRIPTION = "Get all missed messages for topic name and duration, which are missed for each consumer.";
	
	public static final String GET_ALL_PENDING_MSG_SUMMARY = "Get all pending messages for a consumer topic within certain duration";
	public static final String GET_ALL_PENDING_MSG_DESCRIPTION = "Get all pending messages for consumer topic name and duration, which are missed for each consumer.";
	
	public static final String GET_ALL_DISTINCT_TOPIC_SUMMARY = "Get a list of all distinct topics";
	public static final String GET_ALL_DISTINCT_TOPIC_DESCRIPTION = "Get a list of all distinct topics";

}