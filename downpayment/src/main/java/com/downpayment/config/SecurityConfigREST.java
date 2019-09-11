package com.downpayment.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;




@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfigREST extends WebSecurityConfigurerAdapter {
  
	@Autowired
    PasswordEncoder passwordEncoder;
	@Autowired
    private UserDetailsService userDetailsService;
	@Autowired
	private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
 
   
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		   http           
           .antMatcher("/rest/**").csrf().disable()
           .authorizeRequests().anyRequest().authenticated().and()
           .httpBasic().authenticationEntryPoint(authenticationEntryPoint).and()
           .exceptionHandling()
           .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
               
               
           /*.formLogin().usernameParameter("username").passwordParameter("password")
               .loginPage("/login")
               .successHandler(new AuthenticationSuccessHandler() {				
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					redirectStrategy.sendRedirect(request, response, "/home");					
				}
			})
               .permitAll()
               .and()
               .logout()
               .logoutUrl("/logout")
               .logoutSuccessUrl("/login")
               .permitAll();*/
		   
		   }
			@Bean
			public AuthenticationManager customAuthenticationManager2() throws Exception {
				return authenticationManager();
			}
			@Autowired
		    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		        }

	  }


