package com.downpayment.repository;

import org.springframework.data.repository.CrudRepository;

import com.downpayment.domain.Deposit;
import com.downpayment.domain.User;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
 
	Deposit findById(int id);
}
