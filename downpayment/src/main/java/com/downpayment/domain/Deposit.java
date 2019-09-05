package com.downpayment.domain;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Deposit {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private int id;
	private String additionalNote;
	private int amount;
	private String currency;	
	private String sentByUserName;	
	private String sentToUserName;
	@CreationTimestamp
	private Date insertDate;
	@OneToOne(mappedBy="deposit",cascade=CascadeType.ALL)	 
	private Credit userCurrentCredit;
	
	 
	 
	public String getAdditionalNote() {
		return additionalNote;
	}
	public void setAdditionalNote(String additionalNote) {
		this.additionalNote = additionalNote;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	 
	public String getSentByUserName() {
		return sentByUserName;
	}
	public void setSentByUserName(String sentByUserName) {
		this.sentByUserName = sentByUserName;
	}
	public String getSentToUserName() {
		return sentToUserName;
	}
	public void setSentToUserName(String sentToUserName) {
		this.sentToUserName = sentToUserName;
	}
	public Credit getUserCurrentCredit() {
		return userCurrentCredit;
	}
	public void setUserCurrentCredit(Credit userCurrentCredit) {
		this.userCurrentCredit = userCurrentCredit;
	}
	public int getId() {
		return id;
	}
	
	
}
