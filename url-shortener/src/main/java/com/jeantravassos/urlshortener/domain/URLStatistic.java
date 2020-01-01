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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "URL Statistic domain class. Additional info about URLs access are here.")
@Entity
@Data
public class URLStatistic {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes="Auto generated ID.")
	private Long id;

	@ApiModelProperty(notes="Information about the used Brower.")
	private String navigator;

	@ApiModelProperty(notes="Which device was used to access the URL.")
	private String device;

	@ApiModelProperty(notes="OS used to access the URL.")
	private String operatingSystem;

	@CreationTimestamp
	@ApiModelProperty(notes="Date and time of this access.")
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
