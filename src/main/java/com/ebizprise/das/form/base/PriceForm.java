package com.ebizprise.das.form.base;

import java.util.List;

public class PriceForm {

	private String currency;
	private List prices;
	private String assetId;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List getPrices() {
		return prices;
	}

	public void setPrices(List prices) {
		this.prices = prices;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

}
