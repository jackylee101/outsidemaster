package com.ebizprise.das.form.base;

import java.util.Map;

public class ProductForm {

	private Map name;
	private String currency;
	private String type;
	private String country;
	private String dataSource;
	private String minQty;
	private String ISIN;
	private String SYSJUSTiD;
	private String BLOOMBERGiD;
	private String lastUpdateTime;
	private String createTime;
	private String assetId;
	private String scheme;
	private String value;

	public Map getName() {
		return name;
	}

	public void setName(Map name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getMinQty() {
		return minQty;
	}

	public void setMinQty(String minQty) {
		this.minQty = minQty;
	}

	public String getISIN() {
		return ISIN;
	}

	public void setISIN(String iSIN) {
		ISIN = iSIN;
	}

	public String getSYSJUSTiD() {
		return SYSJUSTiD;
	}

	public void setSYSJUSTiD(String sYSJUSTiD) {
		SYSJUSTiD = sYSJUSTiD;
	}

	public String getBLOOMBERGiD() {
		return BLOOMBERGiD;
	}

	public void setBLOOMBERGiD(String bLOOMBERGiD) {
		BLOOMBERGiD = bLOOMBERGiD;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
