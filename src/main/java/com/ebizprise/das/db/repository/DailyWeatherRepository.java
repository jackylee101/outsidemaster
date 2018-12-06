package com.ebizprise.das.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebizprise.das.db.entity.DailyWeather;

public interface DailyWeatherRepository extends
		JpaRepository<DailyWeather, String> {

}
