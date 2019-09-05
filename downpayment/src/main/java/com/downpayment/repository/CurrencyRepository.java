package com.downpayment.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.downpayment.domain.Currency;



public interface CurrencyRepository extends CrudRepository<Currency, Long> {
	List<Currency>findAll();
}
