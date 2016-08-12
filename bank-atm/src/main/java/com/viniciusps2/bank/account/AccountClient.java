package com.viniciusps2.bank.account;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viniciusps2.bank.commons.AtmRequest;
import com.viniciusps2.bank.commons.BankConstants;
import com.viniciusps2.bank.shared.ResponseMessage;
import com.viniciusps2.bank.shared.RestClient;

@Service
public class AccountClient {

	private static String server = BankConstants.BANK_ACCOUNT_API;

	private RestClient<AtmRequest> restClient = new RestClient<>();	
			
	public ResponseMessage<String> withdraw(AtmRequest atmRequest) throws Exception {
		return restClient.post(server + "/withdraw", atmRequest, String.class);
	}

	public void setRestTemplate(RestTemplate restTemplate){
		restClient.setRestTemplate(restTemplate);
	}
		
}