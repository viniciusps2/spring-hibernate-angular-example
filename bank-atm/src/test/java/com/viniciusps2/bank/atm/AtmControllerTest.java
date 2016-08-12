package com.viniciusps2.bank.atm;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context-test.xml" })
public class AtmControllerTest {

	@InjectMocks
	private AtmController controller;

	@Autowired
	private AtmService service;

	@Mock
	private AtmRepository repositoryMock;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void shouldFindAllAtms() throws Exception {

		List<Atm> expected = new ArrayList<Atm>() {
			{
				add(new Atm());
				add(new Atm());
			}
		};
		expected.get(0).setId(5L);
		expected.get(1).setId(7L);

		Mockito.when(repositoryMock.findAll()).thenReturn(expected);

		MvcResult result = mockMvc
				.perform(get("/atms"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		assertThat(result.getResponse().getContentAsString(), hasJsonPath("$[0].id", equalTo(5)));
		assertThat(result.getResponse().getContentAsString(), hasJsonPath("$[1].id", equalTo(7)));
	}
	
	@Test
	public void shouldCreateAtm() throws Exception {
		Atm atm = new Atm();
		atm.setId(5L);

		Mockito.when(repositoryMock.save(Mockito.any(Atm.class))).thenReturn(atm);
		
		String json = "{\"agency\":\"123\"}";
		MvcResult result = mockMvc
				.perform(post("/atms")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andDo(print()) 
				.andExpect(status().isOk())
				.andReturn();
		
		assertThat(result.getResponse().getContentAsString(), hasJsonPath("$.id", equalTo(5)));
	}
	

	@Test
	public void shouldDeleteAtm() throws Exception {
		Atm atm = new Atm();
		atm.setId(5L);

		Mockito.when(repositoryMock.findOne(5L)).thenReturn(atm);
		Mockito.doNothing().when(repositoryMock).delete(Mockito.any(Atm.class));
		
		MvcResult result = mockMvc
				.perform(delete("/atms/5"))
				.andDo(print()) 
				.andExpect(status().isNoContent())
				.andReturn();
		
		Mockito.verify(repositoryMock, Mockito.times(1)).delete(Mockito.any(Atm.class));
	}
}
