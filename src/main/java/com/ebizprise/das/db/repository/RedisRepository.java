package com.ebizprise.das.db.repository;

import java.util.Map;

import com.ebizprise.das.db.model.MyMap;

public interface RedisRepository {

	/**
	 * Return all movies
	 */
	Map<Object, Object> findAllMyMaps();

	/**
	 * Add key-value pair to Redis.
	 */
	void add(MyMap myMap);

	/**
	 * Delete a key-value pair in Redis.
	 */
	void delete(String key, String id);

	/**
	 * find a movie
	 */
	String findMyMap(String key, String id);

}
