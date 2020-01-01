package com.jeantravassos.urlshortener.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class URLDTO implements Serializable {

	private static final long serialVersionUID = 6170211988403161302L;
	
	@NotNull
	@NotEmpty(message = "The URL cannot be empty")
	@URL
	private String url;

	public URLDTO() {
		this.url = "";
	}
	
	public URLDTO(@NotNull @NotEmpty @URL String url) {
		super();
		this.url = url;
	}
}
