package com.tjut.bean;

import java.sql.Date;

public class Customer {
	private int customer_id;
	private String name_cn;
	private String name_en;
	private int sex;
	private String person_id;
	private String email;
	private String phone;
	private String telphone;
	private String nationality;
	private String province;
	private String city;
	private String address;
	private String poscode;
	private Date regdate;
	private String regplace;
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getName_cn() {
		return name_cn;
	}
	public void setName_cn(String name_cn) {
		this.name_cn = name_cn;
	}
	public String getName_en() {
		return name_en;
	}
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getPerson_id() {
		return person_id;
	}
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPoscode() {
		return poscode;
	}
	public void setPoscode(String poscode) {
		this.poscode = poscode;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getRegplace() {
		return regplace;
	}
	public void setRegplace(String regplace) {
		this.regplace = regplace;
	}
	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", name_cn=" + name_cn + ", name_en=" + name_en + ", sex=" + sex
				+ ", person_id=" + person_id + ", email=" + email + ", phone=" + phone + ", telphone=" + telphone
				+ ", nationality=" + nationality + ", province=" + province + ", city=" + city + ", address=" + address
				+ ", poscode=" + poscode + ", regdate=" + regdate + ", regplace=" + regplace + "]";
	}
	
}
