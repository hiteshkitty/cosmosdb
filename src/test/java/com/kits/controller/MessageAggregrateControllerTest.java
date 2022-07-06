package com.kits.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageAggregrateControllerTest {

	@Autowired
	private MockMvc mvc;

	
	@Test
	public void getAllTopics() throws Exception 
	{
	      mvc.perform( MockMvcRequestBuilders
	    	      .get("/cosmos/api/v1/aggregrate/getalltopics")
	    	      .accept(MediaType.APPLICATION_JSON))
	    	      .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
	    	      .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
	}
	
	 
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
