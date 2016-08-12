package com.viniciusps2.bank.atm;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viniciusps2.bank.shared.ApplicationError;
import com.viniciusps2.bank.shared.BaseEntity;

@Entity
public class Atm extends BaseEntity {

	private String agency;
	private String number;
	private String address;
	
	private int notesAmount10 = 0;
	private int notesAmount20 = 0;
	private int notesAmount50 = 0;
	private int notesAmount100 = 0;

	@JsonIgnore
	public Map<Integer, Integer> getNotesDescendingOrder() {
		Map<Integer, Integer> notes = new LinkedHashMap<>();
		notes.put(100, this.notesAmount100);
		notes.put(50, this.notesAmount50);
		notes.put(20, this.notesAmount20);
		notes.put(10, this.notesAmount10);
		return notes;
	}

	public void setNotes(Map<Integer, Integer> notes) {
		this.notesAmount100 = notes.get(100);
		this.notesAmount50 = notes.get(50);
		this.notesAmount20 = notes.get(20);
		this.notesAmount10 = notes.get(10);
	}

	public synchronized AtmResponse withdraw(Integer value) {
		if (value > this.getBalance()) {
			throw new ApplicationError("Insuficient balance on ATM, you can withdraw " + this.getBalance());
		}
		if (value % 10 > 0) {
			throw new ApplicationError("Only multiple of 10");
		}
		int rest = value;
		Map<Integer, Integer> notesAvailable = this.getNotesDescendingOrder();
		Map<Integer, Integer> notesUsed = new LinkedHashMap<>();
		for (int note : notesAvailable.keySet()) {
			while (notesAvailable.get(note) > 0 && rest >= note) {
				rest = rest - note;
				notesAvailable.put(note, notesAvailable.get(note) - 1);
				notesUsed.put(note, (notesUsed.get(note) == null ? 0 : notesUsed.get(note)) + 1);
			}
		}

		if (rest > 0) {
			throw new ApplicationError("Insuficient notes to withdraw this value, you can withdraw " + (value - rest));
		}

		this.setNotes(notesAvailable);
		return new AtmResponse(notesUsed);
	}

	public int getBalance() {
		return (this.notesAmount10 * 10) + (this.notesAmount20 * 20) + (this.notesAmount50 * 50)
				+ (this.notesAmount100 * 100);
	}

	public String getStatus() {
		return this.getBalance() == 0 ? "Unavailable" : "Available";
	}

	public int getNotesAmount10() {
		return notesAmount10;
	}

	public void setNotesAmount10(int notesAmount10) {
		this.notesAmount10 = notesAmount10;
	}

	public int getNotesAmount20() {
		return notesAmount20;
	}

	public void setNotesAmount20(int notesAmount20) {
		this.notesAmount20 = notesAmount20;
	}

	public int getNotesAmount50() {
		return notesAmount50;
	}

	public void setNotesAmount50(int notesAmount50) {
		this.notesAmount50 = notesAmount50;
	}

	public int getNotesAmount100() {
		return notesAmount100;
	}

	public void setNotesAmount100(int notesAmount100) {
		this.notesAmount100 = notesAmount100;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Atm [id=" + this.getId() + ", agency=" + agency + ", number=" + number + ", address=" + address + ", notesAmount10="
				+ notesAmount10 + ", notesAmount20=" + notesAmount20 + ", notesAmount50=" + notesAmount50
				+ ", notesAmount100=" + notesAmount100 + "]";
	}

	
}
