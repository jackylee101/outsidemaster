package com.ebizprise.das.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebizprise.das.db.entity.WeatherLocationMap;

public interface WeatherLocationMapRepository extends
		JpaRepository<WeatherLocationMap, String> {

	// @Query("select a from WeatherLocationMap a ")
	// public List<WeatherLocationMap> findByPredictUsed(String predictUsed);
}