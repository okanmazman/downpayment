package com.downpayment.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.downpayment.domain.Notification;
import com.downpayment.domain.Product;
import com.downpayment.domain.User;
import com.downpayment.service.NotificationService;
import com.downpayment.service.ProductService;
import com.downpayment.service.UserService;


@RestController
public class DownpaymentRESTController {
	@Autowired
	private NotificationService notificationService; 
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	
	
	@RequestMapping(value="/rest/getAllUsers", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<User> getAllUsers() 
	{
		List<User>userList=new ArrayList<User>();
		
		userList=userService.findAll();
		
		for (User user : userList) {
			user.setPassword(null);
		}
		
		return userList;
		 
	}
	
	@RequestMapping(value="/rest/userNotifications", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Set<Notification> getNotifications(@RequestParam(value="username")String username) 
	{
		Set<Notification>userNotifications=new HashSet<Notification>();
		User user=userService.findByUsername(username);
		userNotifications=notificationService.findByUser(user);		
		
		return userNotifications;
		
			
			
		
	}
	@RequestMapping(value="/rest/getProductsByUser", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Set<Product> getProductsByUser(@RequestParam(value="username")String username) 
	{
		 Set<Product>userProducts=new HashSet<Product>();
		 Set<Product> up=new HashSet<Product>();
		 User user=userService.findByUsername(username);
		
		if(user!=null) {
			up=productService.findByUser(user);	
		}
				
	     
		for (Product product : up) 
		{
		if(product.isActive)
			userProducts.add(product);
	    }
		return userProducts;
		
			
			
		
	}
	
	
	@RequestMapping(value="/rest/getNotificationCountByUser", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public int getNotificationCountByUser(@RequestParam(value="username")String username) 
	{
		User user=userService.findByUsername(username);
		Set<Notification>userNotifications=notificationService.findByUser(user);
		for (Notification notification : userNotifications) {
			if(!notification.isRead())
				userNotifications.remove(notification);
		}
		return userNotifications.size();
		
	}
	
	@RequestMapping(value="/rest/changeNotificationStatusRead", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public boolean changeNotificationStatusReadTrue(@RequestParam(value="id")long id) throws Exception
	{
	
		
		try {
			Notification notification=notificationService.findById(id);
			if(notification.isRead())
				notification.setRead(false);
			else if(!notification.isRead())
			{
				notification.setRead(true);
				
			}
				
			notificationService.save(notification);
			return true;
		} catch (Exception e) {
			return false;
			
		}
		
		
		
	}
	
	
	
	
}
