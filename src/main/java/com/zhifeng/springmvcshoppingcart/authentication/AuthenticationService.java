package com.zhifeng.springmvcshoppingcart.authentication;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zhifeng.springmvcshoppingcart.dao.AccountDAO;
import com.zhifeng.springmvcshoppingcart.entity.Account;

//authority manager
//find the account by username
//assign a role to the account by List<GrantedAuthority>
//return the account with authorities
@Service
public class AuthenticationService implements UserDetailsService {
 
	//this is to configure different accounts with different roles
    @Autowired
    private AccountDAO accountDAO;
 
    //find a user by username and assign a role to the user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	//find account
        Account account = accountDAO.findAccount(username);
        System.out.println("Account= " + account);
 
        if (account == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
 
        // get the role of an account
        String role = account.getUserRole();
 
        // the authorities of the account
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
 
        // assign a authority to the account
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
 
        //add the role to the authorities
        grantList.add(authority);
 
        //whether the account is active
        boolean isActive = account.isActive();
 
        //initialize an account with details and its authorities
        UserDetails userDetails = (UserDetails) new User(account.getUserName(), //
                account.getPassword(), isActive, true, true, true, grantList);
 
        return userDetails;
    }
 
}
