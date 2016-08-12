package com.viniciusps2.bank.atm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.viniciusps2.bank.account.AccountClient;
import com.viniciusps2.bank.commons.AtmRequest;
import com.viniciusps2.bank.commons.BankConstants;
import com.viniciusps2.bank.shared.ApplicationError;
import com.viniciusps2.bank.shared.RestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context-test.xml" })
public class AtmServiceTest {

	@Autowired
	private RestClient<String> restClient;
	
	@Autowired
	private AccountClient accountClient;
	
	@Autowired
	private AtmService atmService;
	
	@Autowired
	private AtmRepository repository;

	private MockRestServiceServer mockServer;
	
	private static String accountServer = BankConstants.BANK_ACCOUNT_API;

	@Before
	public void setUp() {
		accountClient.setRestTemplate(restClient.getRestTemplate());
		mockServer = MockRestServiceServer.createServer(restClient.getRestTemplate());
		repository.deleteAll();
	}
	
	@Test
	public void shouldWithdraw() throws Exception {
		mockServer.expect(requestTo(accountServer + "/withdraw"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("{\"message\":\"SUCCESS\"}}", MediaType.APPLICATION_JSON));

		Atm atm = new Atm();
		atm.setNotesAmount10(5);
		repository.save(atm);
		
		AtmRequest atmRequest = new AtmRequest();
		atmRequest.setId(atm.getId());
		atmRequest.setValue(20);
		
		AtmResponse withdraw = atmService.withdraw(atmRequest);
		
		mockServer.verify();
		
		assertEquals(withdraw.getNotesUsed().get(10).toString(), "2");

		Atm savedAtm = repository.findOne(atm.getId());
		
		assertEquals(savedAtm.getBalance(), 30);
	}


	@Test(expected=ApplicationError.class)
	public void whenAccountNotHaveEnoughBalanceShouldThrowError() throws Exception {
		String message = "{\"message\":\"Not enough balance\"}}";
		mockServer.expect(requestTo(accountServer + "/withdraw"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withBadRequest().body(message).contentType(MediaType.APPLICATION_JSON));
 
		Atm atm = new Atm();
		atm.setNotesAmount10(5);
		repository.save(atm);
		
		AtmRequest atmRequest = new AtmRequest();
		atmRequest.setId(atm.getId());
		atmRequest.setValue(20);
		
		try{
			AtmResponse withdraw = atmService.withdraw(atmRequest);
		}catch (ApplicationError e) {
			assertTrue(e.getMessage().contains("Not enough balance"));
			mockServer.verify();
			throw e;
		}

		Atm savedAtm = repository.findOne(atm.getId());	
		assertEquals(savedAtm.getBalance(), 50);
	}

}
