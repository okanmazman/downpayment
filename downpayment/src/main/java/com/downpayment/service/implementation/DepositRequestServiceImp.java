package com.downpayment.service.implementation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Deposit;
import com.downpayment.domain.DepositRequest;
import com.downpayment.domain.User;
import com.downpayment.repository.CreditRepository;
import com.downpayment.repository.DepositRepository;
import com.downpayment.repository.DepositRequestRepository;
import com.downpayment.repository.UserRepository;
import com.downpayment.service.CreditService;
import com.downpayment.service.DepositRequestService;
import com.downpayment.service.DepositService;
 
@Service
public class DepositRequestServiceImp implements DepositRequestService {

	
	@Autowired
	private DepositRequestRepository  depositRequestRepository;

	@Override
	public DepositRequest save(DepositRequest depositRequest) {
		 
		return depositRequestRepository.save(depositRequest);
	}

	@Override
	public DepositRequest findById(long id) {
		
		return depositRequestRepository.findById(id);
	}

	@Override
	public List<DepositRequest> findByUser(User user) {
		// TODO Auto-generated method stub
		return depositRequestRepository.findByUserId(user.getId());
	}

	@Override
	public Set<DepositRequest> findAll() {
		// TODO Auto-generated method stub
		return (Set<DepositRequest>) depositRequestRepository.findAll();
	}
	
	
	 
	 
	 

}
