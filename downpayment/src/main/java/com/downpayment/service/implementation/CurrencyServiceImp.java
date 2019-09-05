package com.downpayment.service.implementation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Currency;
import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.repository.CurrencyRepository;
import com.downpayment.repository.UserRepository;
import com.downpayment.service.CurrencyService;
import com.downpayment.service.UserService;
@Service
public class CurrencyServiceImp implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	public List<Currency> findAll() {
		
		return currencyRepository.findAll();
	}

	@Override
	public Currency save(Currency currency) {
		
		return currencyRepository.save(currency);
	}

	@Override
	public void deleteAll() {
		currencyRepository.deleteAll();
		
	}
	 

}
