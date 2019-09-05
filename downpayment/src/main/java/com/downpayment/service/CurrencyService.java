package com.downpayment.service;

import java.util.List;


import com.downpayment.domain.Currency;
  

public interface CurrencyService {
	List<Currency>findAll();
	Currency save(Currency currency);
	void deleteAll();
}
