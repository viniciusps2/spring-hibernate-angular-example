package com.viniciusps2.bank.commons;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class AtmRequest {
	@NotNull(message = "The field 'Id' must be filled")
	private Long id;
		
	@NotNull(message = "The field 'Value' must be filled")
	@Min(value = 10, message = "The field 'Value' must be at least 10")
	private Integer value;
	
	@NotNull(message = "The field 'Agency number' must be filled")
	private Integer agency;
	
	@NotNull(message = "The field 'Account number' must be filled")
	private Integer number;

	
	@NotEmpty(message = "The field 'Password' must be filled")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
