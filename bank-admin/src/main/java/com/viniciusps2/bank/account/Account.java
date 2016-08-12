package com.viniciusps2.bank.account;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.viniciusps2.bank.shared.BaseEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "agency", "number" }, name = "AgencyNumberUnique") })
public class Account extends BaseEntity {

	private Double currentBalance;

	private String firstName;

	private String lastName;

	@NotNull(message = "The field 'Agency' must be filled")
	private Integer agency;

	@NotNull(message = "The field 'Account number' must be filled")
	private Integer number;

	@NotEmpty(message = "The field 'Password' must be filled")
	private String password;

	public Double getCurrentBalance() {
		return currentBalance != null ? currentBalance : 0;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public void subtractValue(Integer value) {
		this.currentBalance -= value;
	}

}
