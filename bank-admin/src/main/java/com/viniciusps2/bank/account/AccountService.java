package com.viniciusps2.bank.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusps2.bank.shared.ApplicationError;

@Service
public class AccountService {
	@Autowired
	AccountRepository repository;

	@Transactional
	public void withdraw(WithdrawRequest withdrawRequest) {
		Account account = repository.findOneByAgencyAndNumber(withdrawRequest.getAgency(), withdrawRequest.getNumber());
		if (account == null) {
			throw new ApplicationError("Account not found by Agency and Account Number provided.");
		}
		if (!account.getPassword().equals(withdrawRequest.getPassword())) {
			throw new ApplicationError("Wrong password.");
		}
		if (account.getCurrentBalance() < withdrawRequest.getValue()) {
			throw new ApplicationError("Not enough balance in your account to withdraw" +
					(account.getCurrentBalance() > 10 ? "this amount. Try withdraw "+account.getCurrentBalance() : "" ) + ".");
		}
		account.subtractValue(withdrawRequest.getValue());
		repository.save(account);		
	}

	public Account save(Account account) {
		try {
			return repository.save(account);	
		} catch (Exception e) {
			if (e.getMessage().contains("AgencyNumberUnique")) {
				throw new ApplicationError("Agency and Number already used by another account");
			}
			throw e;
		}
	}

}
