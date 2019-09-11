package com.downpayment.domain;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Deposit {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private int id;
	private String additionalNote;
	@Min(value=1 ,message="Please enter a valid amount!")
	private int amount;
	
	private String currency;	
	private String sentByUserName;	
	@NotEmpty
	private String sentToUserName;
	private String productUrl;
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	@CreationTimestamp
	private Date insertDate;
	@OneToOne(mappedBy="deposit",cascade=CascadeType.ALL)	 
	private Credit userCurrentCredit;
	
	@OneToMany(mappedBy="relatedDeposit",cascade=CascadeType.ALL)
	private Set<Notification> userNotifications = new HashSet<Notification>();
	 
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
