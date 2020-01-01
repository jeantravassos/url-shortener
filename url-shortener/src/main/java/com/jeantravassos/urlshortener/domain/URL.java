package com.jeantravassos.urlshortener.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "URL domain class. All fields and validations about URLs are here.")
@Entity
@Data
public class URL {

	@Id
	@ApiModelProperty(notes="The Shortened URL is a required field.")
    @Column(length = 6)
	private String shortened;
	
	@ApiModelProperty(notes="The Original URL is a required field.")
    @Column(nullable = false, length = 2048)
	private String original;
	
	@CreationTimestamp
    @Column(nullable = false, updatable = false)
	private LocalDateTime creationTime;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="url")
	private List<URLStatistic> urlStatistics = new ArrayList<>();
	
	
	@PrePersist
    protected void prePersist() {
		creationTime = LocalDateTime.now();
    }
}