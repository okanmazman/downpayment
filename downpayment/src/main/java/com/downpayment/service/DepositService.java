package com.downpayment.service;



import com.downpayment.domain.Deposit;
 
public interface DepositService {
	
	Deposit save(Deposit deposit);
	Deposit findById(int id);
}
