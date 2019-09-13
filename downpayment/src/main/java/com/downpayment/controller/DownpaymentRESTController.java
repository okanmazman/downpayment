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
	
	@RequestMapping(value="/rest/getNotificationCountByUser", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public int getNotificationCountByUser(@RequestParam(value="username")String username) 
	{
		User user=userService.findByUsername(username);
		Set<Notification>userNotifications=notificationService.findByUser(user);
		return userNotifications.size();
		
	}
	
	@RequestMapping(value="/rest/changeNotificationStatusReadTrue", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public String changeNotificationStatusReadTrue(@RequestParam(value="id")long id) 
	{
		
		try {
			Notification notification=notificationService.findById(id);
			if(notification.isRead())
				notification.setRead(false);
			else
			{
				notification.setRead(true);
				notificationService.save(notification);
			}
				
			
			return "true";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getLocalizedMessage();
		}
		
		
		
	}
	
}
