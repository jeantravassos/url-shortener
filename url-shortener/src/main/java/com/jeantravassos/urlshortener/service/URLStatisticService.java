package com.jeantravassos.urlshortener.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeantravassos.urlshortener.domain.URL;
import com.jeantravassos.urlshortener.domain.URLStatistic;
import com.jeantravassos.urlshortener.exception.URLNotFoundException;
import com.jeantravassos.urlshortener.repository.URLStatisticRepository;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class URLStatisticService {

	@Autowired
	private URLStatisticRepository statisticRepo;
	
	
	public URLStatistic createStatistic(Map<String, String> headers, URL url) {

		String userAgentString = headers.get(HttpHeaders.USER_AGENT.toLowerCase());

		log.info("Getting data from Header info: ", userAgentString);

		UserAgent agent = UserAgent.parseUserAgentString(userAgentString);
		String deviceType = agent.getOperatingSystem().getDeviceType().getName();
		String browser = agent.getBrowser().getName();
		String operatingSystem = agent.getOperatingSystem().getName();

		URLStatistic newStatistic = new URLStatistic(browser, deviceType, operatingSystem, url);
		return statisticRepo.save(newStatistic);
	}
	
	public List<URLStatistic> findAllByShortCode(String shortCode) {
		log.info("Search statitstics for the code: " + shortCode);
		
		Optional<List<URLStatistic>> optional = Optional.ofNullable(statisticRepo.findAllByURLShortened(shortCode));
		return optional.orElseThrow(() -> new URLNotFoundException("Statistics of the URL not found" + shortCode)); 
	}
	
}
