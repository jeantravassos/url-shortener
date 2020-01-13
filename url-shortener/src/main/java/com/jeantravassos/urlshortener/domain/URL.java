package com.jeantravassos.urlshortener.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class URL {

	@Id
    @Column(length = 6)
	private String shortened;
	
    @Column(nullable = false, length = 2048)
	private String original;
	
	@CreationTimestamp
    @Column(nullable = false, updatable = false)
	private LocalDateTime creationTime;

}