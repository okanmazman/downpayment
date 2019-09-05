package com.downpayment.service.implementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Role;
import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.repository.UserRepository;

@Service
public class UserSecurityServiceImp implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if(null == user) {
			throw new UsernameNotFoundException("Username not found");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole urole : user.getUserRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(urole.getRole().getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
		
	}

}
