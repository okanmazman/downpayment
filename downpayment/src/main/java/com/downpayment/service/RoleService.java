package com.downpayment.service;
 
import java.util.List;

import com.downpayment.domain.Role;

public interface RoleService {		
	Role save(Role role);
	List<Role>findAll();
}
