package com.downpayment.service.implementation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Deposit;
import com.downpayment.repository.CreditRepository;
import com.downpayment.repository.DepositRepository;
import com.downpayment.repository.UserRepository;
import com.downpayment.service.CreditService;
import com.downpayment.service.DepositService;
 
@Service
public class DepositServiceImp implements DepositService {

	
	@Autowired
	private DepositRepository depositRepository;
	
	@Override
	public Deposit save(Deposit deposit) {
		
		return depositRepository.save(deposit);
	}

	@Override
	public Deposit findById(int id) {
		// TODO Auto-generated method stub
		return depositRepository.findById(id);
	}

	 
	 
	 

}
