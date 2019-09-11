package com.downpayment.service.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Deposit;
import com.downpayment.domain.Notification;
import com.downpayment.domain.User;
import com.downpayment.repository.CreditRepository;
import com.downpayment.repository.DepositRepository;
import com.downpayment.repository.NotificationRepository;
import com.downpayment.repository.UserRepository;
import com.downpayment.service.CreditService;
import com.downpayment.service.DepositService;
import com.downpayment.service.NotificationService;
 
@Service
public class NotificationServiceImp implements NotificationService {

	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Notification save(Notification notification) {
		// TODO Auto-generated method stub
		return notificationRepository.save(notification);
	}

	@Override
	public Set<Notification> findByUser(User user) {
		
		Set<Notification> sn=new HashSet<Notification>();
		if(notificationRepository.findByUserId(user.getId())!=null)
		return notificationRepository.findByUserId(user.getId());
		else
			return sn;
	}

	
	
	 

	 
	 
	 

}
