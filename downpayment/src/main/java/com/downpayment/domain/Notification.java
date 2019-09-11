package com.downpayment.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Notification {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", nullable = false, updatable = false)
	private long id;
	
	@CreationTimestamp
	private Date notificationDate;
	
	private boolean isRead;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="deposit_id")
	private Deposit relatedDeposit;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	private String notificationText;
	
	
	
	public Date getNotificationDate() {
		return notificationDate;
	}
	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public Deposit getRelatedDeposit() {
		return relatedDeposit;
	}
	public void setRelatedDeposit(Deposit relatedDeposit) {
		this.relatedDeposit = relatedDeposit;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getId() {
		return id;
	}
	public String getNotificationText() {
		return "Amount of "+this.relatedDeposit.getAmount()+" "+this.relatedDeposit.getCurrency()
		+" has been received from "+this.relatedDeposit.getSentByUserName();
	}
	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}
	
	
}
