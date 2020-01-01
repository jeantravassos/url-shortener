package com.jeantravassos.urlshortener.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jeantravassos.urlshortener.domain.URLStatistic;

@Repository
public interface URLStatisticRepository extends JpaRepository<URLStatistic, Long>{

	@Query(value = "SELECT s FROM URLStatistic s where s.url.shortened = ?1 ")
	List<URLStatistic> findAllByURLShortened(String shortened);
	
}
