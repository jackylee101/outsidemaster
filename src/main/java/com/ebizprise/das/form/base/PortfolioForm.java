package com.ebizprise.das.form.base;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebizprise.das.scheduled.service.weather.impl.B2BServiceImpl;

import ch.qos.logback.classic.Logger;

public class PortfolioForm {
	private static final Log logger = LogFactory.getLog(PortfolioForm.class);

	private String prive;
	private String name;
	private String currency;
	private String cfiCode;
	private String inceptioDate;

	private String inceptionPrice;
	private String fubon;
	private String dataSource;
	private List allocation;

	private String lastUpdateTime;
	private String fubonAnnualizedReturn;
	private String fubonAnnualizedVolatility;
	private String assetCodeScheme;
	private String assetCode;
	private List<PortfolioSeriesForm> series;

	public String getPrive() {
		return prive;
	}

	public void setPrive(String prive) {
		this.prive = prive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCfiCode() {
		return cfiCode;
	}

	public void setCfiCode(String cfiCode) {
		this.cfiCode = cfiCode;
	}

	public String getInceptioDate() {
		return inceptioDate;
	}

	public void setInceptioDate(String inceptioDate) {
		this.inceptioDate = inceptioDate;
	}

	public String getInceptionPrice() {
		return inceptionPrice;
	}

	public void setInceptionPrice(String inceptionPrice) {
		this.inceptionPrice = inceptionPrice;
	}

	public String getFubon() {
		return fubon;
	}

	public void setFubon(String fubon) {
		this.fubon = fubon;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public List getAllocation() {
		return allocation;
	}

	public void setAllocation(List allocation) {
		this.allocation = allocation;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getFubonAnnualizedReturn() {
		return fubonAnnualizedReturn;
	}

	public void setFubonAnnualizedReturn(String fubonAnnualizedReturn) {
		this.fubonAnnualizedReturn = fubonAnnualizedReturn;
	}

	public String getFubonAnnualizedVolatility() {
		return fubonAnnualizedVolatility;
	}

	public void setFubonAnnualizedVolatility(String fubonAnnualizedVolatility) {
		this.fubonAnnualizedVolatility = fubonAnnualizedVolatility;
	}

	public String getAssetCodeScheme() {
		return assetCodeScheme;
	}

	public void setAssetCodeScheme(String assetCodeScheme) {
		this.assetCodeScheme = assetCodeScheme;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public List<PortfolioSeriesForm> getSeries() {
		return series;
	}

	public void setSeries(List<PortfolioSeriesForm> series) {
		this.series = series;
	}

	public void showDetail() {
		logger.info("Portfolio:" + getFubon());

	}

}
