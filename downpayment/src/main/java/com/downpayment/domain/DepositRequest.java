package com.downpayment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

@Entity
public class DepositRequest implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private long  id;
	
	@CreationTimestamp
	private Date requestDate;
	
	@OneToOne
	private Status status;
	
	@OneToOne
	private Deposit relatedDeposit;

	private boolean isExpired;
	
	@Range(min = 0l, max=7200,message = "Please select positive numbers Only/max=7200 minutes(5 days)")
	private int expirationTimer;
	
	private String requestType;
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Deposit getRelatedDeposit() {
		return relatedDeposit;
	}

	public void setRelatedDeposit(Deposit relatedDeposit) {
		this.relatedDeposit = relatedDeposit;
	}

	public long getId() {
		return id;
	}

	public int getExpirationTimer() {
		return expirationTimer;
	}

	public void setExpirationTimer(int expirationTimer) {
		this.expirationTimer = expirationTimer;
	}

	 
	
	
	
	
}
