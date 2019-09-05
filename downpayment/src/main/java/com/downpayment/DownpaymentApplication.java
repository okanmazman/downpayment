package com.downpayment;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class DownpaymentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DownpaymentApplication.class, args);
	}
	
	/*@Scheduled(fixedRate = 4000)
	private static void testt()
	{
		final LocalDateTime start = LocalDateTime.now();
		System.out.println("test "+start);
		
	}*/
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
