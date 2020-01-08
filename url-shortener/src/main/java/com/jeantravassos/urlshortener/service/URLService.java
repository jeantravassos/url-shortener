package com.jeantravassos.urlshortener.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.exception.URLNotFoundException;
import com.jeantravassos.urlshortener.helper.URLHelper;
import com.jeantravassos.urlshortener.repository.URLRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class URLService {

	public static final String BREAK_CHARACTERS = "[\n|\r|\t]";

	
	@Autowired
	private URLRepository urlRepository;
		

	public URL createShortURL(String original) {
		log.info("Creating new short URL if the original URL does not exist");
		
		URL urlToReturn = getOriginalURL(original);
		//If the original URL is already registered in the database, it will return it
		if (urlToReturn != null) {
			return urlToReturn;
		}
		
		int firstHash = 0;
		int sixthHash = 5;
		String newShortenedURL = new String();
		while (sixthHash < 31) {
			//Create a new code based on a combination of MD5 and Base64 code generation 
			newShortenedURL = URLHelper.shorteningURL(original, firstHash, sixthHash);
			
			//It considers only 6 digits, when this 6 digit combination does not exist in the database, it will return
			if (urlRepository.findByShortened(newShortenedURL) == null)
				break;
			
			firstHash++;
			sixthHash++;
		}

		urlToReturn = new URL();
		urlToReturn.setOriginal(original);
		urlToReturn.setShortened(newShortenedURL);
		
		return urlRepository.save(urlToReturn);
	}
	
	public URL getOriginalURL(String original) {
		log.info("Trying to get the URL receiving the original as param: " + original);
		return urlRepository.findByOriginal(original);
	}

	public URL getURLByShortened(String shortenedURL) {

		final String code = shortenedURL.replaceAll(BREAK_CHARACTERS, "_");
		log.info("Searching for the shortened URL code: " + code);

		Optional<URL> optional = Optional.ofNullable(urlRepository.findByShortened(code));
		return optional.orElseThrow(() -> new URLNotFoundException("URL not found for the shortened code: " + code));
	}

	public List<URL> getAllURLs()
	{
		log.info("Service searching for all saved URLs");
		return urlRepository.findAll();
	}
}
