package com.jeantravassos.urlshortener;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.dto.URLDTO;
import com.jeantravassos.urlshortener.exception.URLNotFoundException;
import com.jeantravassos.urlshortener.service.URLService;
import com.jeantravassos.urlshortener.service.URLStatisticService;
import com.jeantravassos.urlshortener.web.controller.URLController;

@WebMvcTest(URLController.class)
public class URLControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private URLStatisticService statisticService;
	
	@MockBean
	private URLService urlService;

	URLDTO urlDto;

    @BeforeEach
    void setUpDto() {
        urlDto = URLDTO.builder()
        		.url("http://www.jeantravassos.com")
                .build();
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
	
//	@Test
//	public void whenDTOReceivedShouldCreateCodeAndSave() throws Exception {
//		//Given
//		URL url = new URL();
//		url.setOriginal("http://www.jeantravassos.com");
//		
//		given(urlService.createShortURL(url.getOriginal())).willReturn(url);
//		
//		// When
//		mockMvc.perform(post("/url/")
//				.accept(MediaType.APPLICATION_JSON)
//				.contentType(MediaType.APPLICATION_JSON)
//				.content("{\"url\":http://www.jeantravassos.com}"))
//			.andExpect(status().isOk());
//	}
	
}
