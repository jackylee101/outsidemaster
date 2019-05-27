package com.ebizprise.das.pojo;

import java.math.BigDecimal;

public class Portfolio {
	String name;
	String isin;
	String ccy;
	BigDecimal weight;
	BigDecimal price;
	BigDecimal unit;

	String assetId;

	BigDecimal closePx;
	String localDate;
	BigDecimal dvd;
	BigDecimal nav;

	public Portfolio() {
		super();
	}

	public Portfolio(String name, String isin, String ccy, BigDecimal weight, BigDecimal price, BigDecimal unit) {
		super();
		this.name = name;
		this.isin = isin;
		this.ccy = ccy;
		this.weight = weight;
		this.price = price;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getUnit() {
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public BigDecimal getClosePx() {
		return closePx;
	}

	public void setClosePx(BigDecimal closePx) {
		this.closePx = closePx;
	}

	public String getLocalDate() {
		return localDate;
	}

	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}

	public BigDecimal getDvd() {
		return dvd;
	}

	public void setDvd(BigDecimal dvd) {
		this.dvd = dvd;
	}

	public BigDecimal calcNav() {
		try {
			if ("CASH".equals(name)) {
				nav = unit;
			} else {
				BigDecimal totalValue = closePx.add(dvd);
				nav = totalValue.multiply(unit);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nav;
	}

}
