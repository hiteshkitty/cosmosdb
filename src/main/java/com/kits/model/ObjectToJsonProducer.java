//package com.kits.model;
//
//import java.io.IOException;
//import java.util.Random;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class ObjectToJsonProducer {
//
//	public static void main(String[] args) {
//
//		Producer producer = null;
//
//		for (int i = 1; i < 14; i++) {
//			producer = new Producer();
//
//			producer.setConsumerAppId("A000_" + 2);
//			producer.setCorrelationId("692036f8-447b-4032-ec6c-4f2004d9098e");
//			producer.setOffset(i);
//			producer.setPartitionNumber(2);
//			producer.setProcessingOrder("MAIN");
//			producer.setTimeStamp(System.currentTimeMillis() - generateNumber());
//			producer.setTopicName("testTopic1");
//
//			ObjectMapper Obj = new ObjectMapper();
//
//			// Try block to check for exceptions
//			try {
//
//				// Getting organisation object as a json string
//				String jsonStr = Obj.writeValueAsString(producer);
//
//				// Displaying JSON String on console
//				System.out.println(jsonStr + ",");
//			}
//
//			// Catch block to handle exceptions
//			catch (IOException e) {
//
//				// Display exception along with line number
//				// using printStackTrace() method
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	static int generateNumber() {
//		Random r = new Random();
//		int low = 1000;
//		int high = 1000 * 60 * 15;
//		int result = r.nextInt(high - low) + low;
//		return result;
//	}
//
//}
