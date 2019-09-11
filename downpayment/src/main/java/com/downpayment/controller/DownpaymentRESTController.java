package com.downpayment.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.downpayment.domain.Notification;
import com.downpayment.domain.User;
import com.downpayment.service.NotificationService;
import com.downpayment.service.UserService;


@RestController
public class DownpaymentRESTController {
	@Autowired
	private NotificationService notificationService; 
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/rest/userNotifications", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Set<Notification> getNotifications(@RequestParam(value="username")String username) 
	{
		User user=userService.findByUsername(username);
		Set<Notification>userNotifications=notificationService.findByUser(user);
		return userNotifications;
		
	}
	
}
