package com.downpayment.service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.downpayment.domain.Currency;
import com.downpayment.domain.Status;
  

public interface StatusService {
	Set<Status>findAll();
	Status save(Status status);
	void deleteById(long id);
	
}
