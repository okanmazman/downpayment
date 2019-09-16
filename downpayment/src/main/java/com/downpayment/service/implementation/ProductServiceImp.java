package com.downpayment.service.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Deposit;
import com.downpayment.domain.Product;
import com.downpayment.domain.User;
import com.downpayment.repository.CreditRepository;
import com.downpayment.repository.DepositRepository;
import com.downpayment.repository.ProductRepository;
import com.downpayment.repository.UserRepository;
import com.downpayment.service.CreditService;
import com.downpayment.service.DepositService;
import com.downpayment.service.ProductService;
 
@Service
public class ProductServiceImp implements ProductService {

	
	@Autowired
	private ProductRepository ProductRepository;

	@Override
	public Product save(Product Product) {
		// TODO Auto-generated method stub
		return ProductRepository.save(Product);
	}

	@Override
	public Set<Product> findByUser(User user) {
		
		Set<Product> sn=new HashSet<Product>();
		if(ProductRepository.findByUserId(user.getId())!=null)
		return ProductRepository.findByUserId(user.getId());
		else
			return sn;
	}

	@Override
	public Product findById(long id) {
		// TODO Auto-generated method stub
		return ProductRepository.findById(id);
	}

	
	
	 

	 
	 
	 

}
