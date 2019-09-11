package com.downpayment.utility;

import java.security.SecureRandom;
import java.util.Calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityUtility {
	private static final String SALT = "salt"; // Salt should be protected carefully
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(12,new SecureRandom(UserSecurityUtility.SALT.getBytes()));
	    }
	  
}
