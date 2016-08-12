package com.viniciusps2.bank.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.viniciusps2.bank.shared.ApplicationError;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context-test.xml" })
public class AccountServiceTest {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository repository;
	
	@Before
	public void setUp() {
		repository.deleteAll();
	}
	
	@Test
	public void shouldWithdraw() throws Exception {
		Account account = new Account();
		account.setAgency(1);
		account.setNumber(2);
		account.setPassword("123");
		account.setCurrentBalance(50.0);
		repository.save(account);
		
		WithdrawRequest withdrawRequest = new WithdrawRequest();
		withdrawRequest.setAgency(1);
		withdrawRequest.setNumber(2);
		withdrawRequest.setPassword("123");
		withdrawRequest.setValue(10);
		accountService.withdraw(withdrawRequest);
		
		Account savedAccount = repository.findOne(account.getId());
		
		assertEquals(savedAccount.getCurrentBalance(), new Double(40));
	}

	@Test(expected=ApplicationError.class)
	public void whenAccountNotHaveEnoughBalanceShouldThrowError() throws Exception {
		Account account = new Account();
		account.setAgency(1);
		account.setNumber(2);
		account.setPassword("123");
		account.setCurrentBalance(50.0);
		repository.save(account);
		
		WithdrawRequest withdrawRequest = new WithdrawRequest();
		withdrawRequest.setAgency(1);
		withdrawRequest.setNumber(2);
		withdrawRequest.setPassword("123");
		withdrawRequest.setValue(100);
		accountService.withdraw(withdrawRequest);
		
		try{
			accountService.withdraw(withdrawRequest);
		}catch (ApplicationError e) {
			Account savedAccount = repository.findOne(account.getId());
			System.out.println(savedAccount);
			assertTrue(e.getMessage().contains("Not enough balance"));
			assertEquals(savedAccount.getCurrentBalance(), new Double(50.0));
			
			throw e;
		}
	}
}
