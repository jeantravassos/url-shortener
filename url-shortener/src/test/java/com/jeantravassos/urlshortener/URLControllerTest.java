package com.jeantravassos.urlshortener;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.exception.URLNotFoundException;
import com.jeantravassos.urlshortener.service.URLService;
import com.jeantravassos.urlshortener.service.URLStatisticService;
import com.jeantravassos.urlshortener.web.controller.URLController;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class URLControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private URLStatisticService statisticService;
	
	@Mock
	private URLService urlService;
	
	@InjectMocks
	private URLController controller;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void whenURLShortenedDoesNotExistReturnsNotFound() throws Exception {
		// Given
		String notExistingShortened = "1BruNAJ";

		given(urlService.getURLByShortened(notExistingShortened))
				.willThrow(new URLNotFoundException("URL not found for shortened " + notExistingShortened));

		// When and Then
		mockMvc.perform(get("/url/" + notExistingShortened)).andExpect(status().isNotFound());
	}

	@Test
	public void whenURLShortenedExistsRedirectsToOriginal() throws Exception {
		// Given
		String existingShortened = "YjM2ND";

		URL url = new URL();
		url.setOriginal("http://www.jeantravassos.com");
		url.setShortened(existingShortened);

		given(urlService.getURLByShortened(existingShortened)).willReturn(url);

		// When and Then
		this.mockMvc.perform(get("/url/" + existingShortened)).andExpect(status().is3xxRedirection())
				.andExpect(header().string(HttpHeaders.LOCATION, equalTo(url.getOriginal())));
	}
	
}
