package com.zhifeng.springmvcshoppingcart.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.zhifeng.springmvcshoppingcart.authentication.AuthenticationService;
 
//configure the accessibility of the urls to roles
//roles and corresponding urls
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	//this is the bean of the AuthenticationService
	//autowired to the AuthenticationService class
   @Autowired
   AuthenticationService myDBAauthenticationService;

   //inject the AuthenticationManagerBuilder bean
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
       auth.userDetailsService(myDBAauthenticationService);
 
   }
 
   @Override
   protected void configure(HttpSecurity http) throws Exception {
 
       http.csrf().disable();
 
       // these pages are available to EMPLOYEE or MANAGER
       http.authorizeRequests().antMatchers("/orderList","/order", "/accountInfo")//
               .access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");
 
       // these pages are available to MANAGER
       // only manager can modify the product
       http.authorizeRequests().antMatchers("/product").access("hasRole('ROLE_MANAGER')");
 
       // if not sufficient authorization, AccessDeniedException will throw.
       http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
       // Config for Login Form
       http.authorizeRequests().and().formLogin()//
               // Submit URL of login page.
               .loginProcessingUrl("/j_spring_security_check") // Submit URL
               .loginPage("/login")//
               .defaultSuccessUrl("/accountInfo")//
               .failureUrl("/login?error=true")//
               .usernameParameter("userName")//
               .passwordParameter("password")
               // Config for Logout Page
               // (Go to home page).
               .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
 
   }
}
