package com.downpayment.service;

import java.util.List;
import java.util.Set;

import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;



public interface UserService {
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	User findByUsername(String username);
	
	List<User>findAll();
}
