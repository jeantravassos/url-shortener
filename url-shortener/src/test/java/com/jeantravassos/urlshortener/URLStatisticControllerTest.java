package com.jeantravassos.urlshortener;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jeantravassos.urlshortener.domain.URLStatistic;
import com.jeantravassos.urlshortener.service.URLService;
import com.jeantravassos.urlshortener.service.URLStatisticService;
import com.jeantravassos.urlshortener.web.controller.URLStatisticController;

@ExtendWith(MockitoExtension.class)
public class URLStatisticControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private URLStatisticService statisticService;

	@Mock
	private URLService urlService;
	
	@InjectMocks
	private URLStatisticController controller;
	
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void whenGetURLStatisticsByShortenedReturnsExisting() throws Exception {
		// Given
		String code = "YjM2ND";

		URLStatistic stats = new URLStatistic();
		stats.setAccessedAt(LocalDateTime.now());
		stats.setDevice("iPhone");
		stats.setNavigator("Chrome");
		stats.setOperatingSystem("iOS");
		stats.setUrl(urlService.getURLByShortened(code));
		List<URLStatistic> list = new ArrayList<>();
		list.add(stats);
		
		given(statisticService.findAllByShortCode(code)).willReturn(list);

		// When and Then
		this.mockMvc.perform(get("/statistic/" + code).accept(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
	}
	
}
