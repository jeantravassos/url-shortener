package com.jeantravassos.urlshortener.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class URLStatistic {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String navigator;

	private String device;

	private String operatingSystem;

	@CreationTimestamp
	private LocalDateTime accessedAt;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "shortened", nullable = false)
	private URL url;
	
	public URLStatistic() {
		//Default
	}

	public URLStatistic(
			String nav, 
			String device, 
			String operatingSystem, 
			URL url) {
		this.navigator = nav;
		this.device = device;
		this.operatingSystem = operatingSystem;
		this.url = url;
	}
	
}
