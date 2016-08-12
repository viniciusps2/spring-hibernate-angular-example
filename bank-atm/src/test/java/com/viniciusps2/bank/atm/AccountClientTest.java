package com.viniciusps2.bank.atm;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
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
import com.viniciusps2.bank.shared.ResponseMessage;
import com.viniciusps2.bank.shared.ResponseMessageStatus;
import com.viniciusps2.bank.shared.RestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context-test.xml" })
public class AccountClientTest {

	@Autowired
	private RestClient<String> restClient;
	
	@Autowired
	private AccountClient accountClient;

	private MockRestServiceServer mockServer;
	
	private static String accountServer = BankConstants.BANK_ACCOUNT_API;

	@Before
	public void setUp() {
		accountClient.setRestTemplate(restClient.getRestTemplate());
		mockServer = MockRestServiceServer.createServer(restClient.getRestTemplate());
	}

	@Test
	public void shouldCallAccountsAPI() throws Exception {
		mockServer.expect(requestTo(accountServer + "/withdraw"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("{\"message\":\"SUCCESS\"}", MediaType.APPLICATION_JSON));

		AtmRequest atmRequest = new AtmRequest();
		ResponseMessage<String> result = accountClient.withdraw(atmRequest);

		mockServer.verify();
		assertEquals(result.getStatus(), ResponseMessageStatus.SUCCESS);
	}
}
