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
public class DownpaymentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DownpaymentApplication.class, args);
		

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		final String ACCOUNT_SID = "AC35c2d033c2c73e1e5958f946d9f0841f";
		   final String AUTH_ID = "0aecfb480c8e682b82b5d83130636a4b";
	      Twilio.init(ACCOUNT_SID, AUTH_ID);
		/*Message.creator(new PhoneNumber("+905062993471"), new PhoneNumber("+12563673667"),
		         "Message from Spring Boot Application Okan Mazmanoglu").create();*/
	}
	   
	   
	

}
