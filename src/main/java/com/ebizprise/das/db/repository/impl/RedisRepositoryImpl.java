package com.ebizprise.das.db.repository.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ebizprise.das.db.model.MyMap;
import com.ebizprise.das.db.repository.RedisRepository;

//@Repository
public class RedisRepositoryImpl implements RedisRepository {
	// private static final String KEY = "Movie";

	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@Autowired
	public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	public void add(final MyMap myMap) {
		hashOperations.put(myMap.getKey(), myMap.getId(), myMap.getValue());
	}

	public void delete(String key, String id) {
		hashOperations.delete(key, id);
	}

	public Map<Object, Object> findAllMyMaps(String key) {
		return hashOperations.entries(key);
	}

	@Override
	public String findMyMap(String key, String id) {
		return (String) hashOperations.get(key, id);
	}

	@Override
	public Map<Object, Object> findAllMyMaps() {
		// TODO Auto-generated method stub
		return null;
	}

}
