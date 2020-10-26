package com.tjut.bean;

public class Admin {
	private int employee_id;
	private String admin_num;
	private String admin_code;
	private int manage_id;
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getAdmin_num() {
		return admin_num;
	}
	public void setAdmin_num(String admin_num) {
		this.admin_num = admin_num;
	}
	public String getAdmin_code() {
		return admin_code;
	}
	public void setAdmin_code(String admin_code) {
		this.admin_code = admin_code;
	}
	public int getManage_id() {
		return manage_id;
	}
	public void setManage_id(int manage_id) {
		this.manage_id = manage_id;
	}
	@Override
	public String toString() {
		return "Admin [employee_id=" + employee_id + ", admin_num=" + admin_num + ", admin_code=" + admin_code
				+ ", manage_id=" + manage_id + "]";
	}
	
}
