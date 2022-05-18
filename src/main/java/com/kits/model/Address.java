package com.kits.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    @JsonProperty("AddressLine1")
    private String addressLine1;
    @JsonProperty("AddressLine2")
    private String addressLine2;
    @JsonProperty("AddressLine3")
    private String addressLine3;
    @JsonProperty("IsCurrentAddress")
    private String isCurrentAddress;
    @JsonProperty("IsPermanentAddress")
    private String isPermanentAddress;
    @JsonProperty("Pincode")
    private String pinCode;
    @JsonProperty("City")
    private String city;
    @JsonProperty("State")
    private String state;
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getIsCurrentAddress() {
		return isCurrentAddress;
	}
	public void setIsCurrentAddress(String isCurrentAddress) {
		this.isCurrentAddress = isCurrentAddress;
	}
	public String getIsPermanentAddress() {
		return isPermanentAddress;
	}
	public void setIsPermanentAddress(String isPermanentAddress) {
		this.isPermanentAddress = isPermanentAddress;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Address [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3="
				+ addressLine3 + ", isCurrentAddress=" + isCurrentAddress + ", isPermanentAddress=" + isPermanentAddress
				+ ", pinCode=" + pinCode + ", city=" + city + ", state=" + state + "]";
	}
    
    
}
