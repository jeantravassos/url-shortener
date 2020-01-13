package com.jeantravassos.urlshortener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.exception.URLNotFoundException;
import com.jeantravassos.urlshortener.repository.URLRepository;
import com.jeantravassos.urlshortener.service.URLService;

@ExtendWith(MockitoExtension.class)
public class URLServiceTest {

	@InjectMocks
	private URLService service;

	@Mock
	private URLRepository repository;

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
		assertThat(urlReturned).isNotNull();
		assertEquals(urlReturned, url);
	}
	
	@Test
	public void whenShortenedDoesNotExistThrowsURLNotFoundException() {
		// Given
		String notExistingCode = "1BruNAJ";

		Mockito.when(repository.findByShortened(notExistingCode))
				.thenThrow(new URLNotFoundException("URL not found for shortened code: " + notExistingCode));

		// When
		assertThrows(URLNotFoundException.class, () -> {
			service.getURLByShortened(notExistingCode);
        });
	}
	
	@Test
	public void whenDeleteByIdThenVerifyMock() {
		repository.deleteById("YjM2ND");
		
		verify(repository, atLeastOnce()).deleteById("YjM2ND");
	}
	
	@Test
	void findAll() {
		URL url = new URL();
		List<URL> urlList = new ArrayList<URL>();
		urlList.add(url);

		when(repository.findAll()).thenReturn(urlList);
		
		List<URL> urlsReturned = service.getAllURLs();
		
		verify(repository).findAll();
		
		// Then
		assertThat(urlsReturned).isNotNull();
		assertThat(urlsReturned).hasSize(1);
	}
	
	@DisplayName("BDD - Find All (Behavior Driven Development)")
	@Test
	void shouldReturnAll() {
		
		//given
		URL url = new URL();
		List<URL> urlList = new ArrayList<URL>();
		urlList.add(url);
		given(repository.findAll()).willReturn(urlList);
		
		//when
		List<URL> urlsReturned = service.getAllURLs();
		
		//then
		then(repository).should().findAll();

		assertThat(urlsReturned).isNotNull();
		assertThat(urlsReturned).hasSize(1);
	}
	
}
