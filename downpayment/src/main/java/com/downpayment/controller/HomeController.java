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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

import com.downpayment.domain.Credit;
import com.downpayment.domain.Currency;
import com.downpayment.domain.Deposit;
import com.downpayment.domain.Notification;
import com.downpayment.domain.Product;
import com.downpayment.domain.Role;
import com.downpayment.domain.User;
import com.downpayment.domain.UserRole;
import com.downpayment.service.CreditService;
import com.downpayment.service.NotificationService;
import com.downpayment.service.ProductService;
import com.downpayment.service.RoleService;
import com.downpayment.service.UserService;
import com.downpayment.service.implementation.UserServiceImp;
import com.downpayment.utility.UserSecurityUtility;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class HomeController {
	@Autowired
	private UserService userService; 
	@Autowired
	private CreditService creditService; 
	@Autowired
	private ProductService productService; 
	@Autowired
	private NotificationService notificationService; 
	@Autowired
    PasswordEncoder passwordEncoder;
	 @Autowired
	 private JavaMailSender javaMailSender;
	
	
	//@Autowired
	//private RoleService roleService; 
	@Autowired
	private UserSecurityUtility userSecurityUtility;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/addProduct")
	public String send(Model model,Principal principal,@ModelAttribute("message") String message){
		
		
		
		String username=principal.getName();
		User user=userService.findByUsername(username);
		 
		if(!model.containsAttribute("product"))
		model.addAttribute("product", new Product());
						
		model.addAttribute("message",message);
		return "addProduct";
	}
	
	
	@RequestMapping("/home")
	public String home(Model model){
		 
		Object principal=SecurityContextHolder.getContext().getAuthentication().getName();
		User theUser=userService.findByUsername(principal.toString());
		 
		/*Set<Notification>userNotifications=notificationService.findByUser(theUser);
		model.addAttribute("userNotifications",userNotifications);
		model.addAttribute("userNotificationCount",userNotifications.size());
		model.addAttribute("userdetail", "asd");*/
		Set<Product>userProducts=productService.findByUser(theUser);	
		if(userProducts.size()>0)
		model.addAttribute("userProducts",userProducts);
		return "home";
	}
	
	
	
	@RequestMapping("/login")	
	public String login(){
		return "login";
	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	@RequestMapping(method=RequestMethod.POST)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
	@RequestMapping(value="/register",method = RequestMethod.GET)
	public String register(Model model) {
		System.out.println("register");
	    	
		User user=new User();
		
		//List<Role>roleList=roleService.findAll();
		// model.addAttribute("roles",roleList);
		model.addAttribute("user", user);
		return "register";
	}
	
	 
	
	@RequestMapping(value="/saveUser", method=RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute("user")  User user,BindingResult bindingResults) {
		
		User userx=userService.findByUsername(user.getUsername());
		if(userx!=null&&userx.getId()!=null)
			bindingResults.rejectValue("username","username", "Username exists!");
		
		if (bindingResults.hasErrors()) {
	         return "register";
	      }
		System.out.println("savee");
		PasswordEncoder passEncoder=userSecurityUtility.passwordEncoder();
		String hashedPass=passEncoder.encode(user.getPassword());
		user.setPassword(hashedPass);		
		userService.save(user);		
		Credit cred=new Credit();
		 cred.setAmount(1000);
		 cred.setStatus(true);
		 cred.setUser(user);
		 creditService.save(cred);
		return "redirect:login";
	}
	
	@RequestMapping(value="/addProduct", method=RequestMethod.POST)
	public String saveUser(@ModelAttribute  Product product, Model model,Principal principal,HttpServletRequest request) {
		
		Deposit deposit =new Deposit();
		String username=principal.getName();
		User user=userService.findByUsername(username);
		deposit.setSentToUserName(user.getUsername());
		deposit.setAmount(product.requestedValue);
		product.setUser(user);
		productService.save(product);				
		
		return "redirect:home";
	}
	
	 // Display the form
    @RequestMapping("/forgot-password")
    public String displayResetPassword(Model model) 
    {                      
    	model.addAttribute("user", new User());
    	return "forgot-password";
    }
    
    @RequestMapping(value="/forgot-password-post", method=RequestMethod.POST)
    public String resetPassword(@ModelAttribute("user")  User user,BindingResult bindingResults) {               
    	User userx=userService.findByUsername(user.getUsername());
		if(userx==null) {
			bindingResults.rejectValue("username","username", "There is no user exist!");
		}			
		else if (userx!=null&&!userx.getEmail().equals(user.getEmail()))
		{
			bindingResults.rejectValue("email","email", "There is no such an email for this username!");
		}
		else
		{
			try {
			    // Instantiate our encoder
				String xxx="1234";

			    // Right before saving the user on registration, we encode the password
			    userx.setPassword(passwordEncoder.encode(xxx));
			    userService.save(userx);
			    
			    
				// Create the email
	            /*SimpleMailMessage mailMessage = new SimpleMailMessage();
	            mailMessage.setTo(userx.getEmail());
	            mailMessage.setSubject("Your Password E-deposit system!");
	            mailMessage.setFrom("test-email@gmail.com");
	            mailMessage.setText("Your username= "+userx.getUsername()+" Your password= "+xxx);*/

	            // Send the email
	            //javaMailSender.send(mailMessage);

	            //Send SMS
	            /*String mesaj="Your username= "+userx.getUsername()+" Your password= "+xxx;
	            final String ACCOUNT_SID = "";
	 		    final String AUTH_ID = "";
	 	       Twilio.init(ACCOUNT_SID, AUTH_ID);
	 	       Message.creator(new PhoneNumber(""), new PhoneNumber(""),
	 		         mesaj).create();*/
	            	
	            //modelAndView.addObject("message", "Request to reset password received. Check your inbox for the reset link.");
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	
		}
		if (bindingResults.hasErrors()) 
		{			
	         return "forgot-password";
	    }
		
		    	return "redirect:login";
    }
	
}
