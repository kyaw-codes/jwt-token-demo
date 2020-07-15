package com.kyaw.jobsearch.security;

import com.kyaw.jobsearch.model.entity.Account;
import com.kyaw.jobsearch.model.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = service.findById(email);
        return User.builder()
                .username(email)
                .password(account.getPassword())
                .roles(account.getRole().name())
                .disabled(false)
                .accountLocked(account.isDeleted())
                .build();
    }
}
