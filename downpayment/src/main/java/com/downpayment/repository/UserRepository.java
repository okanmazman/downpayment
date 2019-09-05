package com.downpayment.repository;

import org.springframework.data.repository.CrudRepository;

import com.downpayment.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	User findByEmail(String email);
	User findById(int id);
}
