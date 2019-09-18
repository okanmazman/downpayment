package com.downpayment.service;




import java.util.List;
import java.util.Set;

import com.downpayment.domain.DepositRequest;
import com.downpayment.domain.User;
 
public interface DepositRequestService {
	
	DepositRequest save(DepositRequest depositRequest);
	DepositRequest findById(long id);
	List<DepositRequest>findByUser(User user);
	Set<DepositRequest>findAll();
}
