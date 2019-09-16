package com.downpayment.service;



import java.util.Optional;
import java.util.Set;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Product;
import com.downpayment.domain.User;
 
public interface ProductService {
	
	Product save(Product Product);
	Set<Product>findByUser(User user);
	Product findById(long id);
}
