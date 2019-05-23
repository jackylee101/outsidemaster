package com.ebizprise.das.form.userInfo;

import com.ebizprise.das.form.system.StatusForm;
import com.fasterxml.jackson.annotation.JsonInclude;

public class UserInfoCheckForm extends StatusForm {

	private String custId;

//	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrname;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrLevelId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrFungroupId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrEvtgroupId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String usrPic;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer prodClDrop;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer prodClNew;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer prodClDiscrete;
	private String id;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errCode;
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getUsrname() {
		return usrname;
	}
	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}
	public String getUsrLevelId() {
		return usrLevelId;
	}
	public void setUsrLevelId(String usrLevelId) {
		this.usrLevelId = usrLevelId;
	}
	public String getUsrFungroupId() {
		return usrFungroupId;
	}
	public void setUsrFungroupId(String usrFungroupId) {
		this.usrFungroupId = usrFungroupId;
	}
	public String getUsrEvtgroupId() {
		return usrEvtgroupId;
	}
	public String getUsrPic() {
		return usrPic;
	}
	public void setUsrPic(String usrPic) {
		this.usrPic = usrPic;
	}
	public void setUsrEvtgroupId(String usrEvtgroupId) {
		this.usrEvtgroupId = usrEvtgroupId;
	}
	public Integer getProdClDrop() {
		return prodClDrop;
	}
	public void setProdClDrop(Integer prodClDrop) {
		this.prodClDrop = prodClDrop;
	}
	public Integer getProdClNew() {
		return prodClNew;
	}
	public void setProdClNew(Integer prodClNew) {
		this.prodClNew = prodClNew;
	}
	public Integer getProdClDiscrete() {
		return prodClDiscrete;
	}
	public void setProdClDiscrete(Integer prodClDiscrete) {
		this.prodClDiscrete = prodClDiscrete;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

}
