package com.tjut.bean;

public class Manage {
	private int employee_id;
	private String manage_num;
	private String manage_code;
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getManage_num() {
		return manage_num;
	}
	public void setManage_num(String manage_num) {
		this.manage_num = manage_num;
	}
	public String getManage_code() {
		return manage_code;
	}
	public void setManage_code(String manage_code) {
		this.manage_code = manage_code;
	}
	@Override
	public String toString() {
		return "Manage [employee_id=" + employee_id + ", manage_num=" + manage_num + ", manage_code=" + manage_code
				+ "]";
	}
	
	
}
