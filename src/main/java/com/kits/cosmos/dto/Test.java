package com.kits.cosmos.dto;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Test {

	public static void main(String[] args) {

		 List<Integer> list = Arrays.asList(-9, -18, 0, 25, 4);
		  
	        // Using Stream.min() with reverse
	        // comparator to get maximum element.
	        Optional<Integer> var = list.stream()
	                    .min(Comparator.reverseOrder());
	  
	        // IF var is empty, then output will be Optional.empty
	        // else value in var is printed.
	        if(var.isPresent()){
	        System.out.println(var.get());
	        }
	        else{
	            System.out.println("NULL");
	        }
	}
}
