package com.downpayment.service;




import java.util.List;

import com.downpayment.domain.DepositRequest;
import com.downpayment.domain.User;
 
public interface DepositRequestService {
	
	DepositRequest save(DepositRequest depositRequest);
	DepositRequest findById(int id);
	List<DepositRequest>findByUser(User user);
}
