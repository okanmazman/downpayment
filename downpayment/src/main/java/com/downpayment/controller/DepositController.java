package com.downpayment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.downpayment.domain.Role;
import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.service.CreditService;
import com.downpayment.service.CurrencyService;
import com.downpayment.service.DepositService;
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
	
	@RequestMapping("/send")
	public String send(Model model,Principal principal,@ModelAttribute("message") String message){
		currencyService.deleteAll();
		Currency cur1=new Currency();
		cur1.setName("Turkish Liras");
		cur1.setStatus("Active");
		
		String username=principal.getName();
		User user=userService.findByUsername(username);
		currencyService.save(cur1);
		
		Credit credit=new Credit();
		Optional<Credit> cr=creditService.findByUser(user);
		List<Currency>currencyList=new ArrayList<Currency>();
		currencyList=currencyService.findAll();
		model.addAttribute("currency",currencyList);		
		model.addAttribute("deposit", new Deposit());
		model.addAttribute("userCredit", cr.get());
		model.addAttribute("message",message);
		return "send";
	}
	

	@RequestMapping(value="/sendDeposit", method=RequestMethod.POST)
	public String saveUser(@ModelAttribute  Deposit deposit, HttpServletRequest request,Model model,RedirectAttributes redirectAttributes) {
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getName();
		User sentByUser=userService.findByUsername(principal.toString());
		deposit.setSentByUserName(sentByUser.getUsername());
		try {
		User sentToUser=userService.findByUsername(deposit.getSentToUserName().toString());
		depositService.save(deposit);
		Optional<Credit> crSentBy=creditService.findByUser(sentByUser);
		Optional<Credit> crSentTo=creditService.findByUser(sentToUser);
		float delta=deposit.getAmount();
		
		crSentBy.get().setAmount(crSentBy.get().getAmount()-delta);
		crSentTo.get().setAmount(crSentTo.get().getAmount()+delta);		
		
			
			creditService.save(crSentBy.get());
			creditService.save(crSentTo.get());
			
			redirectAttributes.addAttribute("message","Succesfully sent!");
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("message","Error!Couldn't send!Try again!");
		}		
		
		return "redirect:/send";
	}
	
	@RequestMapping("/request")
	public String home(Model model){
		return "request";
	}
	
	
	
	
}
