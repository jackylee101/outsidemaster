package com.ebizprise.das.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "DailyWeather")
public class DailyWeather implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "KEY")
	private String key;

	@Lob
	@Column(name = "JSONDATA", columnDefinition = "CLOB")
	private String jsonData;

	public DailyWeather() {
	}

	public DailyWeather(String key, String jsonData) {
		super();
		this.key = key;
		this.jsonData = jsonData;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}