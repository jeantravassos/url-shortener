package com.jeantravassos.urlshortener;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.jeantravassos.urlshortener.domain.URLStatistic;
import com.jeantravassos.urlshortener.service.URLService;
import com.jeantravassos.urlshortener.service.URLStatisticService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class URLStatisticControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private URLStatisticService statisticService;

	@MockBean
	private URLService urlService;
	

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
