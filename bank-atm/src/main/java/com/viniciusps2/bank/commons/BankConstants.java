package com.viniciusps2.bank.commons;

public class BankConstants {
	public static String BANK_ACCOUNT_API = System.getenv("BANK_ACCOUNT_API") != null ?
			System.getenv("BANK_ACCOUNT_API") : "http://localhost:9090/bank-admin/accounts";
}
