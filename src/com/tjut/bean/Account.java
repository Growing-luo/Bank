package com.tjut.bean;

public class Account {
	private int account_id;
	private int customer_id;
	private String account_num;
	private String account_code;
	private float balance;
	private int status;
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getAccount_code() {
		return account_code;
	}
	public void setAccount_code(String account_code) {
		this.account_code = account_code;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", customer_id=" + customer_id + ", account_num=" + account_num
				+ ", account_code=" + account_code + ", balance=" + balance + ", status=" + status + "]";
	}
	
	
}
