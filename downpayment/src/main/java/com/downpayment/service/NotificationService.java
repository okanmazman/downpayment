package com.downpayment.service;



import java.util.Optional;
import java.util.Set;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Notification;
import com.downpayment.domain.User;
 
public interface NotificationService {
	
	Notification save(Notification notification);
	Set<Notification>findByUser(User user);
	Notification findById(long id);
}
