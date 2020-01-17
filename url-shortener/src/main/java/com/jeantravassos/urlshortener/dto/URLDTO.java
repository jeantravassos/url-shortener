package com.jeantravassos.urlshortener.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "URL parameter form the POST API", description = "It's the body of the POST request.")
@Data
public class URLDTO implements Serializable {

	private static final long serialVersionUID = 6170211988403161302L;

	@ApiParam(value = "Main property of the POST api.")
	@NotNull
	@NotEmpty(message = "The URL cannot be empty")
	@URL
	private String url;

	public URLDTO() {
		this.url = "";
	}
	
	@Builder
	public URLDTO(@NotNull @NotEmpty @URL String url) {
		super();
		this.url = url;
	}
}
