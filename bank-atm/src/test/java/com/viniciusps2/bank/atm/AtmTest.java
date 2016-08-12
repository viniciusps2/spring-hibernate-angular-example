package com.viniciusps2.bank.atm;

import org.junit.Test;

import com.viniciusps2.bank.shared.ApplicationError;

import junit.framework.Assert;

public class AtmTest {

	@Test
	public void whenAtmHaveSuficientBalanceShoudWhithdraw() {
		Atm atm = new Atm();
		atm.setNotesAmount10(3);
		atm.setNotesAmount50(1);
		atm.setNotesAmount100(1);

		atm.withdraw(80);
		
		Assert.assertEquals(atm.getBalance(), 100);
	}
	

	@Test(expected=ApplicationError.class)
	public void whenAtmHaveInsuficientBalanceShoudNotWhithdraw() {
		Atm atm = new Atm();
		atm.setNotesAmount10(3);
		
		try {
			atm.withdraw(800);	
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Insuficient balance"));
			throw e;
		}
	}


	@Test(expected=ApplicationError.class)
	public void whenAtmHaveInsuficientBalanceShoudNotWhithdrawAndShouldInformMinimumValue() {
		Atm atm = new Atm();
		atm.setNotesAmount10(3);
		
		try {
			atm.withdraw(800);	
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Insuficient balance"));
			Assert.assertTrue(e.getMessage().contains("can withdraw 30"));
			throw e;
		}
	}
	

	@Test(expected=ApplicationError.class)
	public void whenWhithdrawNotMultipleShouldThrowException() {
		Atm atm = new Atm();
		atm.setNotesAmount10(10);
		
		try {
			atm.withdraw(55);	
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Only multiple of 10"));
			throw e;
		}
	}
}
