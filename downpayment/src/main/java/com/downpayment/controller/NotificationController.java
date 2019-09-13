package com.downpayment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.downpayment.domain.Notification;
import com.downpayment.domain.Role;
import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.service.CreditService;
import com.downpayment.service.CurrencyService;
import com.downpayment.service.DepositService;
import com.downpayment.service.NotificationService;
import com.downpayment.service.RoleService;
import com.downpayment.service.UserService;
import com.downpayment.service.implementation.UserServiceImp;
import com.downpayment.utility.UserSecurityUtility;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class NotificationController {
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
	
	 
	@RequestMapping("/notification-modal")
	public String notificationmodal(Model model)
	{
		return "notification-modal";
	}
	
	@RequestMapping("/getNotificationsByUser")
	public String notifications(Model model,Principal principal){
		User user=userService.findByUsername(principal.getName());
		Set<Notification>userNotifications=notificationService.findByUser(user);
		model.addAttribute("userNotifications",userNotifications);
		return "redirect:/notification-modal";
	}
	
	 
	
	 
	
	
	
	
}
