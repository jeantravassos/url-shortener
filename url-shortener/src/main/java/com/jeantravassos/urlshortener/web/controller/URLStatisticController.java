package com.jeantravassos.urlshortener.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeantravassos.urlshortener.domain.URLStatistic;
import com.jeantravassos.urlshortener.service.URLStatisticService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/statistic")
@Slf4j
public class URLStatisticController {

	@Autowired
	private URLStatisticService statisticService;
	
	@GetMapping(path = "/{code}")
	public ResponseEntity<List<URLStatistic>> getStatisticsByURLShortcode(@PathVariable String code) {
		log.info("Service searching statistics for shortcode: " + code);
		
		List<URLStatistic> listToReturn = statisticService.findAllByShortCode(code);
		
		return ResponseEntity.ok(listToReturn);
	}

	
}
