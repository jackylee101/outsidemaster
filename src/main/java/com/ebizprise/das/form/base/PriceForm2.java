package com.ebizprise.das.form.base;

import org.apache.commons.lang.StringUtils;

public class PriceForm2 {

	private String Id;
	private String type;
	private String assetId;
	private String localDate;
	private String closePx;
	private String dataSource;

	public PriceForm2() {
		super();
	}

	public PriceForm2(PriceForm2 priceForm2) {
		super();
		this.Id = priceForm2.getId();
		this.type = priceForm2.getType();
		this.assetId = priceForm2.getAssetId();
		this.localDate = priceForm2.getLocalDate();
		this.closePx = priceForm2.getClosePx();
		this.dataSource = priceForm2.getDataSource();
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getLocalDate() {
		return localDate;
	}

	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}

	public String getClosePx() {
		String ss = closePx;
		int dot = StringUtils.indexOf(closePx, ".");
		int len = closePx.length();
		for (int i = 0; i < 5 - (len - dot); i++) {
			ss += '0';
		}

		return ss;
	}

	public void setClosePx(String closePx) {
		this.closePx = closePx;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

}
