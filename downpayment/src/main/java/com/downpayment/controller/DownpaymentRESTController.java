package com.downpayment.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.downpayment.domain.Credit;
import com.downpayment.domain.DepositRequest;
import com.downpayment.domain.Notification;
import com.downpayment.domain.Product;
import com.downpayment.domain.Status;
import com.downpayment.domain.User;
import com.downpayment.service.CreditService;
import com.downpayment.service.DepositRequestService;
import com.downpayment.service.NotificationService;
import com.downpayment.service.ProductService;
import com.downpayment.service.StatusService;
import com.downpayment.service.UserService;


@RestController
public class DownpaymentRESTController {
	@Autowired
	private NotificationService notificationService; 
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DepositRequestService depositRequestService;
	@Autowired 
	private StatusService statusService;
	@Autowired
	private CreditService creditService;
	@Autowired
	private ProductService prodcutService;
	
	
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
	@RequestMapping(value="/rest/userDepositRequests", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<DepositRequest> getDepositRequests(@RequestParam(value="username")String username) 
	{
		List<DepositRequest>userDepositRequests=new ArrayList<DepositRequest>();
		User user=userService.findByUsername(username);
		userDepositRequests=depositRequestService.findByUser(user);		
		
		return userDepositRequests;
		
			
			
		
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
	
	@RequestMapping(value="/rest/acceptDepositRequest", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public boolean applyDepositRequest(@RequestParam(value="requestId")long id) throws Exception
	{
		try {
		DepositRequest dreq=depositRequestService.findById(id);
		Status status=new Status();
		float delta=(float) dreq.getRelatedDeposit().getAmount();
		
		User sentByUser=userService.findByUsername(dreq.getRelatedDeposit().getSentByUserName());
		User sentToUser=userService.findByUsername(dreq.getRelatedDeposit().getSentToUserName());
		Optional<Credit> crSentBy=creditService.findByUser(sentByUser);
		Optional<Credit> crSentTo=creditService.findByUser(sentToUser);
		crSentBy.get().setAmount(crSentBy.get().getAmount()-delta);
		crSentTo.get().setAmount(crSentTo.get().getAmount()+delta);		
		creditService.save(crSentBy.get());
		creditService.save(crSentTo.get()); 
		Set<Status>statusList= statusService.findAll();
		 for (Status statusItem : statusList) {
			 if(statusItem.getStatusName().equals("Accepted"))
				 status=statusItem;
		}
		
		
			
			if(dreq.getStatus().getStatusName().equals("Pending"))
				dreq.setStatus(status);
			 	
			depositRequestService.save(dreq);
			
			Notification notification=new Notification();
			notification.setRelatedDeposit(dreq.getRelatedDeposit());
			notification.setUser(sentToUser);
			notification.setNotificationText("The deposit transaction value of "+dreq.getRelatedDeposit().getAmount() +" from "+ sentByUser.getUsername()+" to "+sentToUser.getUsername() +" completed succesfully!");
			notification.setRead(false);
			notificationService.save(notification);
			return true;
		} catch (Exception e) {
			return false;
			
		}
		
	
		
	}

	@RequestMapping(value="/rest/rejectDepositRequest", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public boolean rejectDepositRequest(@RequestParam(value="requestId")long id) throws Exception
	{
		Status status=new Status();
		 Set<Status>statusList= statusService.findAll();
		 for (Status statusItem : statusList) {
			 if(statusItem.getStatusName().equals("Rejected"))
				 status=statusItem;
		}
		
		try {
			DepositRequest dreq=depositRequestService.findById((int) id);
			if(dreq.getStatus().getStatusName().equals("Pending"))
				dreq.setStatus(status);
				
				
				
			depositRequestService.save(dreq);
			return true;
		} catch (Exception e) {
			return false;
			
		}
		
		
		
	}
	
	/*@Scheduled(fixedRate = 100000)
	public void checkExpirationsOfRequests(){
		Set<DepositRequest> depositRequestsAll=depositRequestService.findAll();
		LocalDateTime localDateTime = LocalDateTime.now(); //get current date time
        System.out.println("Current Time " + localDateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = localDateTime.format(formatter).trim();
        LocalDateTime formatedDateTime = LocalDateTime.parse(formatDateTime, formatter);
        
		for (DepositRequest depositRequest : depositRequestsAll) {
			LocalDateTime depReqExpDate=LocalDateTime.parse(depositRequest.getExpirationDate().trim(),formatter);
			int xx=depReqExpDate.compareTo(formatedDateTime);
			if(depReqExpDate.compareTo(formatedDateTime)>0)
			{
				depositRequest.setExpired(false);
			}
			else if(depReqExpDate.compareTo(formatedDateTime)<0)
			{
				depositRequest.setExpired(true);
			}
			depositRequestService.save(depositRequest);
			depReqExpDate=null;
		}
		
	}*/
}
