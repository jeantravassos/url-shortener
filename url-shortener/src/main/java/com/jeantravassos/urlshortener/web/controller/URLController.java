package com.jeantravassos.urlshortener.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.dto.URLDTO;
import com.jeantravassos.urlshortener.service.URLService;
import com.jeantravassos.urlshortener.service.URLStatisticService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/url")
@Slf4j
public class URLController {

	public static final String CHARS_TO_REPLACE = "[\n|\r|\t]";
	
	@Autowired
    private URLService urlService;
	
	@Autowired
	private URLStatisticService statisticService;
	
	@ApiOperation(value = "Shortening a long URL",
		    notes = "This method receives a long URL as parameter, if not found, send a specific error, otherwise it will shorten it")
	@PostMapping
	public ResponseEntity<URL> create(@Valid @RequestBody URLDTO urlDTO) {

		URL url = urlService.createShortURL(urlDTO.getUrl());
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{code}")
				.buildAndExpand(url.getShortened())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Redirecting to a original URL",
		    notes = "This method receives a key of the original URL as parameter, and returns a redirection for the original one")
	@GetMapping(path = "/{code}")
	public ResponseEntity<URL> redirect(@PathVariable String code,
			@RequestHeader Map<String, String> headersMap) {

		URL url = urlService.getURLByShortened(code);

		//Create statistic related to the current URL access redirection
		statisticService.createStatistic(headersMap, url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(url.getOriginal()));

		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@ApiOperation(value = "Get all URLs saved",
		    notes = "Finds all the URLs registered in the database")
	@GetMapping(path = "/")
	public List<URL> findAll() {
		log.info("Find all URLs");

		return urlService.getAllURLs();
	}
	
}
