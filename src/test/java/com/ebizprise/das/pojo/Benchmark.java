package com.ebizprise.das.pojo;

import java.util.ArrayList;
import java.util.List;

public class Benchmark {
	String name;
	List<Portfolio> portfolioList;

	public Benchmark() {
		super();
		portfolioList = new ArrayList();
	}

	public Benchmark(String name) {
		super();
		this.name = name;
		portfolioList = new ArrayList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Portfolio> getPortfolioList() {
		return portfolioList;
	}

	public void setPortfolioList(List<Portfolio> portfolioList) {
		this.portfolioList = portfolioList;
	}

	public void addPortfolio(Portfolio portfolio) {
		this.portfolioList.add(portfolio);
	}

}
