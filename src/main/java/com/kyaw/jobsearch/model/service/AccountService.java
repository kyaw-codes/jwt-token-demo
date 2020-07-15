package com.kyaw.jobsearch.model.service;

import com.kyaw.jobsearch.model.dto.LoginDto;
import com.kyaw.jobsearch.model.dto.LoginResultDto;
import com.kyaw.jobsearch.model.dto.SignUpDto;
import com.kyaw.jobsearch.model.entity.Account;
import com.kyaw.jobsearch.model.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepo repo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager auth;

    public LoginResultDto login(LoginDto login) {
        return login(login.getEmail(), login.getPassword());
    }

    public LoginResultDto signUp(SignUpDto dto) {
        Account account = new Account();
        account.setEmail(dto.getEmail());
        account.setName(dto.getName());
        account.setPassword(encoder.encode(dto.getPassword()));
        account.setRole(dto.getRole());
        repo.save(account);

        return login(dto.getEmail(), dto.getPassword());
    }

    public Long count() {
        return repo.count();
    }

    // internal login
    private LoginResultDto login(String email, String password) {
        LoginResultDto dto = new LoginResultDto();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email,password,null);
        Authentication authentication = auth.authenticate(token);

        if (authentication.isAuthenticated()) {
            Account account = findById(email);
            dto.setName(account.getName());
            dto.setRole(account.getRole());
            dto.setSuccess(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            dto.setMessage("Authentication Fails!");
        }

        return dto;
    }

    public Account findById(String email) {
        return repo.findById(email).orElseThrow();
    }
}
