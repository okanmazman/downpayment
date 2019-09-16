package com.downpayment.service.implementation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.repository.UserRepository;
import com.downpayment.service.UserService;
@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		
		return userRepository.save(user);
	}
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

}
