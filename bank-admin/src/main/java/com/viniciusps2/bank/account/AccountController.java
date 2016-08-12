package com.viniciusps2.bank.account;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	AccountRepository repository;

	@Autowired
	AccountService service;
	

	@RequestMapping(method = RequestMethod.GET)
	public List<Account> all() {
		return repository.findAll();
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public Account create(@Validated @RequestBody Account account) {
		return service.save(account);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Account view(@PathVariable Long id) {
		return repository.findOne(id);
	}

	
	@RequestMapping(method = RequestMethod.PUT)
	public Account update(@Validated @RequestBody Account account) {
		return service.save(account);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/withdraw")
	@ResponseStatus(value = HttpStatus.OK)
	public void withdraw(@RequestBody @Valid WithdrawRequest withdrawRequest) {
		service.withdraw(withdrawRequest);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="{id}")
	public ResponseEntity<Account> update(@PathVariable Long id) {
		Account account = repository.findOne(id);
		if (account == null) {
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}

		repository.delete(account);
		return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
		
	}


}
