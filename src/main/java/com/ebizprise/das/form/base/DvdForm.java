package com.ebizprise.das.form.base;

import java.util.List;

public class DvdForm {

	private String currency;
	private List dividends;
	private String assetId;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List getDividends() {
		return dividends;
	}

	public void setDividends(List dividends) {
		this.dividends = dividends;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
}
