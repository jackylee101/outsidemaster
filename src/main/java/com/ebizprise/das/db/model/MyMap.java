package com.ebizprise.das.db.model;

import java.io.Serializable;

public class MyMap implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private String key;
	private String id;
	private String value;

	public MyMap(String key, String id, String name) {
		this.key = key;
		this.id = id;
		this.value = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MyMap{key=" + key + ", id=" + id + ", value=" + value + "}";
	}
}
