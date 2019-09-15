package com.downpayment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.downpayment.domain.Credit;
import com.downpayment.domain.Currency;
import com.downpayment.domain.Deposit;
import com.downpayment.domain.DepositRequest;
import com.downpayment.domain.Notification;
import com.downpayment.domain.Role;
import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.service.CreditService;
import com.downpayment.service.CurrencyService;
import com.downpayment.service.DepositRequestService;
import com.downpayment.service.DepositService;
import com.downpayment.service.NotificationService;
import com.downpayment.service.RoleService;
import com.downpayment.service.UserService;
import com.downpayment.service.implementation.UserServiceImp;
import com.downpayment.utility.UserSecurityUtility;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class DepositController {
	@Autowired
	private CurrencyService currencyService; 
	
	@Autowired
	private DepositService depositService;
	
	@Autowired
	private CreditService creditService;
	 
	@Autowired
	private UserService userService; 
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired DepositRequestService depositRequestService;
	
	@RequestMapping("/send")
	public String send(Model model,Principal principal,@ModelAttribute("message") String message){
		
		
		
		String username=principal.getName();
		User user=userService.findByUsername(username);
		
		
		
		Optional<Credit> cr=creditService.findByUser(user);
		List<Currency>currencyList=new ArrayList<Currency>();
		currencyList=currencyService.findAll();
		model.addAttribute("currency",currencyList);		
		if(!model.containsAttribute("deposit"))
		model.addAttribute("deposit", new Deposit());
		
		model.addAttribute("userCredit", cr.get());
		
		model.addAttribute("message",message);
		return "send";
	}
	

	@RequestMapping(value="/sendDeposit", method=RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute  Deposit deposit,BindingResult bindingResult, HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) 
		{
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deposit", bindingResult);
            redirectAttributes.addFlashAttribute("deposit", deposit);
            return "redirect:/send";
		}
		
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getName();
		User sentByUser=userService.findByUsername(principal.toString());
		deposit.setSentByUserName(sentByUser.getUsername());
		
		try {
		User sentToUser=userService.findByUsername(deposit.getSentToUserName().toString());
		if(sentToUser==null) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.depositRequest", bindingResult);
			  redirectAttributes.addFlashAttribute("deposit", deposit);
        redirectAttributes.addFlashAttribute("message","User does not exist!");
        redirectAttributes.addFlashAttribute("messageValue",2);
        return "redirect:/request";
			
		}
		depositService.save(deposit);
		Optional<Credit> crSentBy=creditService.findByUser(sentByUser);
		Optional<Credit> crSentTo=creditService.findByUser(sentToUser);
		
		float delta=(float) deposit.getAmount();
		if(crSentBy.get().getAmount()<delta) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deposit", bindingResult);
            redirectAttributes.addFlashAttribute("deposit", deposit);
            redirectAttributes.addFlashAttribute("message","Insufficient credit!!");
            redirectAttributes.addFlashAttribute("messageValue",2);
            return "redirect:/send";
		}
			
		crSentBy.get().setAmount(crSentBy.get().getAmount()-delta);
		crSentTo.get().setAmount(crSentTo.get().getAmount()+delta);		
		
			
			creditService.save(crSentBy.get());
			creditService.save(crSentTo.get());
			
			redirectAttributes.addFlashAttribute("message","Succesfully sent!");
			redirectAttributes.addFlashAttribute("messageValue",1);
			
			Notification notification=new Notification();
			notification.setRelatedDeposit(deposit);
			notification.setUser(sentToUser);
			notification.setNotificationText(sentToUser.getFirstName()+" "+sentToUser.getLastName()+" sent you the deposit value of "+deposit.getAmount() );
			notification.setRead(false);
			notificationService.save(notification);
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message",e.getMessage()+"--"+e.getLocalizedMessage());
			redirectAttributes.addFlashAttribute("messageValue",2);
		}		
		
		return "redirect:/send";
	}
	
	
	
	@RequestMapping("/request")
	public String request(Model model,Principal principal,@ModelAttribute("message") String message){
		 
		String username=principal.getName();
		User user=userService.findByUsername(username);
		Optional<Credit> cr=creditService.findByUser(user);
		
		DepositRequest dr=new DepositRequest();
		Deposit dp=new Deposit();
		dp.setSentByUserName(username);
		dp.setUserCurrentCredit(cr.get());
		
		dr.setRelatedDeposit(dp);
		
		
		
			
		if(!model.containsAttribute("depositRequest"))
		model.addAttribute("depositRequest", dr);
		 
		model.addAttribute("message",message);
		 
		return "request";
	}
	
	@RequestMapping("/requestDeposit")
	public String requestDeposit(@Valid @ModelAttribute  DepositRequest depositRequest,HttpServletRequest request,BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes)
	{
		User sentToUser=userService.findByUsername(depositRequest.getRelatedDeposit().getSentToUserName());
		
		if(sentToUser==null) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.depositRequest", bindingResult);
        depositRequest.getRelatedDeposit().setUserCurrentCredit(depositRequest.getRelatedDeposit().getUserCurrentCredit());
			redirectAttributes.addFlashAttribute("depositRequest", depositRequest);
        redirectAttributes.addFlashAttribute("message","User does not exist!");
        redirectAttributes.addFlashAttribute("messageValue",2);
        return "redirect:/request";
			
		}
		if (bindingResult.hasErrors()) 
		{
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.depositRequest", bindingResult);
            redirectAttributes.addFlashAttribute("depositRequest", depositRequest);
            return "redirect:/request";
		}
		
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getName();
		User sentByUser=userService.findByUsername(principal.toString());
		Optional<Credit> crSentBy=creditService.findByUser(sentByUser);
		
		
		float delta=(float) depositRequest.getRelatedDeposit().getAmount();
		if(crSentBy.get().getAmount()<delta) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.depositRequest", bindingResult);
            redirectAttributes.addFlashAttribute("depositRequest", depositRequest);
            redirectAttributes.addFlashAttribute("message","Insufficient credit!!");
            redirectAttributes.addFlashAttribute("messageValue",2);
            return "redirect:/request";
		}
		
		try {
			
			try {
				depositService.save(depositRequest.getRelatedDeposit());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			depositRequestService.save(depositRequest);
			Notification notification=new Notification();
			notification.setRelatedDeposit(depositRequest.getRelatedDeposit());
			notification.setUser(sentToUser);
			notification.setNotificationText("You have a deposit request from "+sentByUser.getFirstName()+" "+sentByUser.getLastName() +"for product "
							+depositRequest.getRelatedDeposit().getProductUrl()
							+".Deposit Value= "+depositRequest.getRelatedDeposit().getAmount());
			notification.setRead(false);
			notificationService.save(notification);
			redirectAttributes.addFlashAttribute("message","Request succesfully sent!");
			redirectAttributes.addFlashAttribute("messageValue",1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			redirectAttributes.addFlashAttribute("message",e.getMessage()+"--"+e.getLocalizedMessage());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.depositRequest", bindingResult);
            redirectAttributes.addFlashAttribute("depositRequest", depositRequest);
			redirectAttributes.addFlashAttribute("messageValue",2);
		}
		return  "redirect:/request";
	}
	
	
	
	
}
