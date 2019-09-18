package com.downpayment.repository;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.data.repository.CrudRepository;

import com.downpayment.domain.Currency;
import com.downpayment.domain.Status;



public interface StatusRepository extends CrudRepository<Status, Long> {
	Set<Status>findAll();
	
}
