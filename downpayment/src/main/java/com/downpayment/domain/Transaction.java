package com.downpayment.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Transaction {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private long id;
	private Date transactionDate;	
	private Deposit depositItem;
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Deposit getDepositItem() {
		return depositItem;
	}
	public void setDepositItem(Deposit depositItem) {
		this.depositItem = depositItem;
	}
	public long getId() {
		return id;
	}
	
	
	
}
