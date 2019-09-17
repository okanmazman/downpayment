package com.downpayment.service.implementation;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Currency;
import com.downpayment.domain.Status;
import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.repository.CurrencyRepository;
import com.downpayment.repository.StatusRepository;
import com.downpayment.repository.UserRepository;
import com.downpayment.service.CurrencyService;
import com.downpayment.service.StatusService;
import com.downpayment.service.UserService;
@Service
public class StatusServiceImp implements StatusService {

	@Autowired
	private StatusRepository statusRepository;

	 

	@Override
	public Status save(Status status) {
		// TODO Auto-generated method stub
		return statusRepository.save(status);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		statusRepository.deleteById(id);;
	}

	@Override
	public Set<Status> findAll() {
		// TODO Auto-generated method stub
		return statusRepository.findAll();
	}

		 

}
