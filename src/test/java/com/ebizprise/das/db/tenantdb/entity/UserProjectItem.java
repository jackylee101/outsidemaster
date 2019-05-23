package com.ebizprise.das.db.tenantdb.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the USER_PROJECT_ITEM database table.
 * 
 */
@Entity
@Table(catalog = "TENANT_DB", schema = "dbo", name = "USER_PROJECT_ITEM")
public class UserProjectItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRJ_ITM_ID")
	private String prjItmId;

	@Column(name = "CUST_ID")
	private String custId;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Column(name = "IS_DEL")
	private String isDel;

	@Column(name = "IS_SHOW")
	private String isShow;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "MODIFIED_USER")
	private String modifiedUser;

	@Column(name = "NAME")
	private String name;

	public UserProjectItem() {
	}

	public String getPrjItmId() {
		return prjItmId;
	}

	public void setPrjItmId(String prjItmId) {
		this.prjItmId = prjItmId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getIsShow() {
		return this.isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedUser() {
		return this.modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}