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

//find the account by username
//assign a role to the account by List<GrantedAuthority>
//return the account with authorities
@Service
public class AuthenticationService implements UserDetailsService {
 
	//this is to configure different accounts with different roles
    @Autowired
    private AccountDAO accountDAO;
 
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
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
 
        // assign a role to the account
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
 
        grantList.add(authority);
 
        boolean isActive = account.isActive();
 
        //initialize an account with details
        UserDetails userDetails = (UserDetails) new User(account.getUserName(), //
                account.getPassword(), isActive, true, true, true, grantList);
 
        return userDetails;
    }
 
}
