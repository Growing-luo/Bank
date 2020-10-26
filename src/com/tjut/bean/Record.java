package com.tjut.bean;

import java.util.Date;

public class Record {
	private int record_id;
	private int account_id;
	private int op_type;
	private float transaction_amount;
	private float free;
	private float balance;
	private Date transaction_date;
	private String transaction_place;
	private int inaccount_id;
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getOp_type() {
		return op_type;
	}
	public void setOp_type(int op_type) {
		this.op_type = op_type;
	}
	public float getTransaction_amount() {
		return transaction_amount;
	}
	public void setTransaction_amount(float transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	public float getFree() {
		return free;
	}
	public void setFree(float free) {
		this.free = free;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Date getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}
	public String getTransaction_place() {
		return transaction_place;
	}
	public void setTransaction_place(String transaction_place) {
		this.transaction_place = transaction_place;
	}
	public int getInaccount_id() {
		return inaccount_id;
	}
	public void setInaccount_id(int inaccount_id) {
		this.inaccount_id = inaccount_id;
	}
	@Override
	public String toString() {
		return "Record [record_id=" + record_id + ", account_id=" + account_id + ", op_type=" + op_type
				+ ", transaction_amount=" + transaction_amount + ", free=" + free + ", balance=" + balance
				+ ", transaction_date=" + transaction_date + ", transaction_place=" + transaction_place
				+ ", inaccount_id=" + inaccount_id + "]";
	}
	
	
}
