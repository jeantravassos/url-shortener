package com.jeantravassos.urlshortener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.exception.URLNotFoundException;
import com.jeantravassos.urlshortener.repository.URLRepository;
import com.jeantravassos.urlshortener.service.URLService;

public class URLServiceTest {

	@InjectMocks
	private URLService service;

	@Mock
	private URLRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenShortenedExistsReturnsUrl() {
		// Given
		String existingShortened = "YjM2ND";

		URL url = new URL();
		url.setOriginal("http://www.jeantravassos.com");
		url.setShortened(existingShortened);

		Mockito.when(repository.findByShortened(existingShortened)).thenReturn(url);

		// When
		URL urlReturned = service.getURLByShortened(existingShortened);

		// Then
		Assert.assertEquals(urlReturned, url);
	}
	
	@Test(expected = URLNotFoundException.class)
	public void whenShortenedDoesNotExistThrowsURLNotFoundException() {
		// Given
		String notExistingCode = "1BruNAJ";

		Mockito.when(repository.findByShortened(notExistingCode))
				.thenThrow(new URLNotFoundException("URL not found for shortened code: " + notExistingCode));

		// When
		service.getURLByShortened(notExistingCode);
	}
	
}
