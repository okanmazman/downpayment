package com.downpayment.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Credit;
import com.downpayment.domain.User;
import com.downpayment.repository.CreditRepository;
 
import com.downpayment.service.CreditService;
 
@Service
public class CreditServiceImp implements CreditService {

	@Autowired
	private CreditRepository creditRepository;

	@Override
	public Credit save(Credit credit) {
		
		return creditRepository.save(credit);
	}

	@Override
	public Optional<Credit> findByUser(User user) {
		Long userId=user.getId();
		return creditRepository.findById(userId);
	}

	
	 

	 

	 
	 

}
