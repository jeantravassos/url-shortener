package com.jeantravassos.urlshortener;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.exception.URLNotFoundException;
import com.jeantravassos.urlshortener.service.URLService;
import com.jeantravassos.urlshortener.service.URLStatisticService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class URLControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private URLStatisticService statisticService;
	
	@MockBean
	private URLService urlService;


	@Test
	public void whenURLShortenedDoesNotExistReturnsNotFound() throws Exception {
		// Given
		String notExistingShortened = "1BruNAJ";

		given(urlService.getURLByShortened(notExistingShortened))
				.willThrow(new URLNotFoundException("URL not found for shortened " + notExistingShortened));

		// When and Then
		this.mockMvc.perform(get("/url/" + notExistingShortened)).andExpect(status().isNotFound());
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
