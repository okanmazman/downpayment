package com.downpayment.service;

import java.util.List;
import java.util.Optional;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Currency;
import com.downpayment.domain.User;
  

public interface CreditService {
	Credit  save(Credit credit);
	Optional<Credit> findByUser(User user);
}
