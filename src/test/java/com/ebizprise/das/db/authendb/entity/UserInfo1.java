package com.ebizprise.das.db.authendb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the USER_INFO database table.
 * 
 */
@Entity
@Table(catalog = "AUTHEN_DB", schema = "dbo", name = "USER_INFO")
// @NamedQuery(name = "UserInfo.findAll", query = "SELECT u FROM UserInfo u")
public class UserInfo1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CUST_ID")
	private String custId;

	@Column(name = "COMPANY_ID")
	private String companyId;

	@Column(name = "ACT_DATE")
	private Date actDate;

	@Column(name = "BE_MEMBER_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beMemberDate;

	@Column(name = "COUNTRY_CODE")
	private String countryCode;

	@Column(name = "CREATE_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "EXPIRED_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiredDate;

	@Column(name = "FILE_URL")
	private String fileUrl;

	// @Lob
	// @Column(name = "ICON")
	// private byte[] icon;

	@Column(name = "IS_ACT")
	private String isAct;

	@Column(name = "IS_DEL")
	private String isDel;

	@Column(name = "JOB_TITLE")
	private String jobTitle;

	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;

	@Column(name = "MODIFIED_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifiedDate;

	@Column(name = "MODIFIED_USER")
	private String modifiedUser;

	@Column(name = "PROD_CL_DISCRETE")
	private Integer prodClDiscrete;

	@Column(name = "PROD_CL_DROP")
	private Integer prodClDrop;

	@Column(name = "PROD_CL_NEW")
	private Integer prodClNew;

	@Column(name = "PROD_SCLSET")
	private Integer prodSclset;

	@Column(name = "SET_DTFIRST")
	private String setDtfirst;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "USR_EVTGROUP_ID")
	private String usrEvtgroupId;

	@Column(name = "USR_FUNGROUP_ID")
	private String usrFungroupId;

	@Column(name = "USR_LEVEL_ID")
	private String usrLevelId;

	@Column(name = "USRNAME")
	private String usrname;

	@Column(name = "USRPWD")
	private String usrpwd;

	public UserInfo1() {
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getActDate() {
		return this.actDate;
	}

	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}

	public Date getBeMemberDate() {
		return this.beMemberDate;
	}

	public void setBeMemberDate(Date beMemberDate) {
		this.beMemberDate = beMemberDate;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	// public byte[] getIcon() {
	// return this.icon;
	// }
	//
	// public void setIcon(byte[] icon) {
	// this.icon = icon;
	// }

	public String getIsAct() {
		return this.isAct;
	}

	public void setIsAct(String isAct) {
		this.isAct = isAct;
	}

	public String getIsDel() {
		return this.isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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

	public Integer getProdClDiscrete() {
		return this.prodClDiscrete;
	}

	public void setProdClDiscrete(Integer prodClDiscrete) {
		this.prodClDiscrete = prodClDiscrete;
	}

	public Integer getProdClDrop() {
		return this.prodClDrop;
	}

	public void setProdClDrop(Integer prodClDrop) {
		this.prodClDrop = prodClDrop;
	}

	public Integer getProdClNew() {
		return this.prodClNew;
	}

	public void setProdClNew(Integer prodClNew) {
		this.prodClNew = prodClNew;
	}

	public Integer getProdSclset() {
		return this.prodSclset;
	}

	public void setProdSclset(Integer prodSclset) {
		this.prodSclset = prodSclset;
	}

	public String getSetDtfirst() {
		return this.setDtfirst;
	}

	public void setSetDtfirst(String setDtfirst) {
		this.setDtfirst = setDtfirst;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUsrEvtgroupId() {
		return this.usrEvtgroupId;
	}

	public void setUsrEvtgroupId(String usrEvtgroupId) {
		this.usrEvtgroupId = usrEvtgroupId;
	}

	public String getUsrFungroupId() {
		return this.usrFungroupId;
	}

	public void setUsrFungroupId(String usrFungroupId) {
		this.usrFungroupId = usrFungroupId;
	}

	public String getUsrLevelId() {
		return this.usrLevelId;
	}

	public void setUsrLevelId(String usrLevelId) {
		this.usrLevelId = usrLevelId;
	}

	public String getUsrname() {
		return this.usrname;
	}

	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}

	public String getUsrpwd() {
		return this.usrpwd;
	}

	public void setUsrpwd(String usrpwd) {
		this.usrpwd = usrpwd;
	}

}