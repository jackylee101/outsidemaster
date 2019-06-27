package com.ebizprise.das.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ModelPortfolio {
	String modelName;
	String targetDate;

	BigDecimal nav;
	List<Portfolio> portfolioList;

	BigDecimal currencyNav;

	public ModelPortfolio() {
		super();
		portfolioList = new ArrayList();
	}

	public ModelPortfolio(String modelName, String targetDate, BigDecimal nav) {
		super();
		this.modelName = modelName;
		this.targetDate = targetDate;
		this.nav = nav;
		portfolioList = new ArrayList();
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	public BigDecimal getCurrencyNav() {
		return currencyNav;
	}

	public void setCurrencyNav(BigDecimal currencyNav) {
		this.currencyNav = currencyNav;
	}

	public BigDecimal getNav() {
		return nav;
	}

	public void setNav(BigDecimal nav) {
		this.nav = nav;
	}

	public List<Portfolio> getPortfolioList() {
		return portfolioList;
	}

	public void setPortfolioList(List<Portfolio> portfolioList) {
		this.portfolioList = portfolioList;
	}

	public void addPortfolio(Portfolio po) {
		portfolioList.add(po);

	}

	public BigDecimal calcNav() {
		currencyNav = new BigDecimal(0);
		for (int i = 0; i < portfolioList.size(); i++) {
			Portfolio portfolio = portfolioList.get(i);
			currencyNav = currencyNav.add(portfolio.calcNav());
		}
		return currencyNav;
	}

}