package com.kits.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerResponse {
    private String statusCode;
    private String message;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ProducerResponse [statusCode=" + statusCode + ", message=" + message + "]";
	}
    
    
}
