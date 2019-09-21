package com.downpayment;

 

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

 import com.twilio.rest.api.v2010.account.Message;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
@EnableScheduling
public class DownpaymentApplication  {

	public static void main(String[] args) {
		SpringApplication.run(DownpaymentApplication.class, args);
		

	}

	

	
	   
	   
	

}
