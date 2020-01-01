package com.jeantravassos.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeantravassos.urlshortener.domain.URL;

@Repository
public interface URLRepository extends JpaRepository<URL, String>{

	URL findByOriginal(String original);
	
	URL findByShortened(String shortened);
	
}
