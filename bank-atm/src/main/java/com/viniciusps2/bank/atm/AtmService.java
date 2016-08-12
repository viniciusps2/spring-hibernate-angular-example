package com.viniciusps2.bank.atm;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciusps2.bank.account.AccountClient;
import com.viniciusps2.bank.commons.AtmRequest;
import com.viniciusps2.bank.shared.ResponseMessage;

@Service
public class AtmService {

	@Autowired
	private AtmRepository repository;
	
	@Autowired
	private AccountClient accountClient;
	
	@Transactional
	public AtmResponse withdraw(AtmRequest atmRequest) throws Exception {
		Atm atm = repository.findOne(atmRequest.getId());
		AtmResponse response = atm.withdraw(atmRequest.getValue());
		
		ResponseMessage<String> withdraw = accountClient .withdraw(atmRequest);
		
		repository.save(atm);
		return response;
	}
}
