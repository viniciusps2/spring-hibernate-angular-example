package com.viniciusps2.bank.shared;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestClient <T> {
    private RestTemplate restTemplate = new RestTemplate();
    
	private HttpHeaders headers;
	
	public RestClient(){
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
	}

	public ResponseMessage<String> post(String uri, T json, Class clazz)
			throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<T> requestEntity = new HttpEntity<T>(json, headers);
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, clazz);
			return new ResponseMessage<String>(responseEntity.getBody());
		} catch (HttpStatusCodeException e) {
			System.out.println("HTTP ERROR!!" + e.getResponseBodyAsString());
			ObjectMapper mapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			ResponseMessage<String> response = mapper.readValue(e.getResponseBodyAsString(), ResponseMessage.class);
			throw new ApplicationError(response.getMessage());
		}
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
}
