package com.ebizprise.das.form.base;

public class DvdForm2 {

	private String Id;
	private String exDate;
	private String value;
	private String dataSource;
	private String assetId;

	public DvdForm2() {
		super();
	}

	public DvdForm2(DvdForm2 dvdForm2) {
		super();
		this.Id = dvdForm2.getId();
		this.exDate = dvdForm2.getExDate();
		this.value = dvdForm2.getValue();
		this.dataSource = dvdForm2.getDataSource();
		this.assetId = dvdForm2.getAssetId();
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getExDate() {
		return exDate;
	}

	public void setExDate(String exDate) {
		this.exDate = exDate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

}
