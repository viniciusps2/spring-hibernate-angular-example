package com.viniciusps2.bank.atm;

import java.util.Map;

public class AtmResponse {
	Map<Integer, Integer> notesUsed;
	
	public AtmResponse(Map<Integer, Integer> notesUsed) {
		this.notesUsed = notesUsed;
	}

	public Map<Integer, Integer> getNotesUsed() {
		return notesUsed;
	}

	public void setNotesUsed(Map<Integer, Integer> notesUsed) {
		this.notesUsed = notesUsed;
	}
	
	
}
