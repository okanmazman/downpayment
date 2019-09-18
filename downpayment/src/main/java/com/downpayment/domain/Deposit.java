package com.downpayment.domain;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Deposit implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private int id;
	private String additionalNote;
	@Min(value=1 ,message="Please enter a valid amount!")
	
	
	private double amount;
	
		
	private String sentByUserName;
	private long sentByUserId;
	
	@NotEmpty
	private String sentToUserName;
	
	private long sentToUserId;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Product relatedProduct;
	
 
	
	//getters and setters
	
	
	public Product getRelatedProduct() {
		return relatedProduct;
	}
	public void setRelatedProduct(Product relatedProduct) {
		this.relatedProduct = relatedProduct;
	}
	public long getSentByUserId() {
		return sentByUserId;
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
	public Credit userCurrentCredit;
	
	@OneToOne(mappedBy="relatedDeposit",cascade=CascadeType.ALL)	 
	private DepositRequest requestDeposit;
	
	@OneToMany(mappedBy="relatedDeposit",cascade=CascadeType.ALL)
	private Set<Notification> userNotifications = new HashSet<Notification>();
	 
	public String getAdditionalNote() {
		return additionalNote;
	}
	public void setAdditionalNote(String additionalNote) {
		this.additionalNote = additionalNote;
	}
	public  double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public long getSentBtUserId() {
		return sentByUserId;
	}
	public void setSentByUserId(long sentByUserId) {
		this.sentByUserId = sentByUserId;
	}
	public long getSentToUserId() {
		return sentToUserId;
	}
	public void setSentToUserId(long sentToUserId) {
		this.sentToUserId = sentToUserId;
	}
	 
	
	}
	
	

