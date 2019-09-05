package com.downpayment.repository;

import org.springframework.data.repository.CrudRepository;

import com.downpayment.domain.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
	Role  findByName(String name);	
	Role findByRoleId(int roleId);
	
}