package com.ebizprise.das.db.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "WEATHER_LOCATION_MAP")
public class WeatherLocationMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String areaid;

	private String area;

	private String c1;

	private String c10;

	private String c11;

	private String c12;

	private String c15;

	private String c16;

	private String c17;

	private String c2;

	private String c3;

	private String c4;

	private String c5;

	private String c6;

	private String c7;

	private String c8;

	private String c9;

	@Column(name = "create_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@Column(name = "create_user")
	private String createUser;

	private String distric;

	private String latitude;

	private String longitude;

	private String prov;

	public WeatherLocationMap() {
	}

	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getC1() {
		return this.c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	public String getC10() {
		return this.c10;
	}

	public void setC10(String c10) {
		this.c10 = c10;
	}

	public String getC11() {
		return this.c11;
	}

	public void setC11(String c11) {
		this.c11 = c11;
	}

	public String getC12() {
		return this.c12;
	}

	public void setC12(String c12) {
		this.c12 = c12;
	}

	public String getC15() {
		return this.c15;
	}

	public void setC15(String c15) {
		this.c15 = c15;
	}

	public String getC16() {
		return this.c16;
	}

	public void setC16(String c16) {
		this.c16 = c16;
	}

	public String getC17() {
		return this.c17;
	}

	public void setC17(String c17) {
		this.c17 = c17;
	}

	public String getC2() {
		return this.c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	public String getC3() {
		return this.c3;
	}

	public void setC3(String c3) {
		this.c3 = c3;
	}

	public String getC4() {
		return this.c4;
	}

	public void setC4(String c4) {
		this.c4 = c4;
	}

	public String getC5() {
		return this.c5;
	}

	public void setC5(String c5) {
		this.c5 = c5;
	}

	public String getC6() {
		return this.c6;
	}

	public void setC6(String c6) {
		this.c6 = c6;
	}

	public String getC7() {
		return this.c7;
	}

	public void setC7(String c7) {
		this.c7 = c7;
	}

	public String getC8() {
		return this.c8;
	}

	public void setC8(String c8) {
		this.c8 = c8;
	}

	public String getC9() {
		return this.c9;
	}

	public void setC9(String c9) {
		this.c9 = c9;
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

	public String getDistric() {
		return this.distric;
	}

	public void setDistric(String distric) {
		this.distric = distric;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getProv() {
		return this.prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

}