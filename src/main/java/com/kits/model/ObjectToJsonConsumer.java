//package com.kits.model;
//
//import java.io.IOException;
//import java.util.Random;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class ObjectToJsonConsumer {
//
//	public static void main(String[] args) {
//
//		Consumer consumer = null;
//
//		for (int i = 1; i < 10; i++) {
//			if (i%3==0)
//				continue;
//			consumer = new Consumer();
//
//			consumer.setConsumerAppId("A000_" + 1);
//			consumer.setCorrelationId("235156f8-447b-4032-fb6b-4f2004d9098e");
//			consumer.setOffset(i);
//			consumer.setPartitionNumber(1);
//			consumer.setProcessingOrder("MAIN");
//			consumer.setTimeStamp(System.currentTimeMillis() - generateNumber());
//			consumer.setTopicName("testTopic1");
//
//			ObjectMapper Obj = new ObjectMapper();
//
//			// Try block to check for exceptions
//			try {
//
//				// Getting organisation object as a json string
//				String jsonStr = Obj.writeValueAsString(consumer);
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
