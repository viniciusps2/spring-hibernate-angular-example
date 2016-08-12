package com.viniciusps2.bank.atm;

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
import org.springframework.web.bind.annotation.RestController;

import com.viniciusps2.bank.commons.AtmRequest;
import com.viniciusps2.bank.shared.ResponseMessage;

@RestController
@RequestMapping("/atms")
public class AtmController {

	@Autowired
	private AtmRepository repository;
	
	@Autowired
	private AtmService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<Atm> all() {
		return repository.findAll();
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public Atm create(@Validated @RequestBody Atm atm) {
		System.out.println(atm);
		return repository.save(atm);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{atmId}")
	public Atm view(@PathVariable Long atmId) {
		return repository.findOne(atmId);
	}

	
	@RequestMapping(method = RequestMethod.PUT)
	public Atm update(@Validated @RequestBody Atm atm) {
		return repository.save(atm);
	}

	@RequestMapping(method = RequestMethod.POST, value = "withdraw")
	public ResponseMessage<AtmResponse> withdraw(@RequestBody @Valid AtmRequest atmRequest) throws Exception {
		return new ResponseMessage<AtmResponse>(service.withdraw(atmRequest));
	}

	
	@RequestMapping(method = RequestMethod.DELETE, value="{id}")
	public ResponseEntity<Atm> update(@PathVariable Long id) {
		Atm atm = repository.findOne(id);
		if (atm == null) {
			return new ResponseEntity<Atm>(HttpStatus.NOT_FOUND);
		}

		repository.delete(atm);
		return new ResponseEntity<Atm>(HttpStatus.NO_CONTENT);
		
	}

}
