package com.viniciusps2.bank.account;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WithdrawRequest {
	
	@NotNull(message = "The field 'Value' must be filled")
    @Min(value = 1, message = "The field 'Value' must be at least 1")
	private Integer value;
	
	private Integer agency;
	
	private Integer number;
	
	private String password;
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getAgency() {
		return agency;
	}
	public void setAgency(Integer agency) {
		this.agency = agency;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
	public String toString() {
		return "WithdrawRequest [value=" + value + ", agency=" + agency + ", number=" + number + ", password="
				+ password + "]";
	}

	

}
