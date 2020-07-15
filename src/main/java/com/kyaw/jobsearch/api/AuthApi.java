package com.kyaw.jobsearch.api;

import com.kyaw.jobsearch.model.dto.LoginDto;
import com.kyaw.jobsearch.model.dto.LoginResultDto;
import com.kyaw.jobsearch.model.dto.SignUpDto;
import com.kyaw.jobsearch.model.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthApi {

    @Autowired
    private AccountService service;

    @PostMapping("login")
    public LoginResultDto login(@RequestBody LoginDto login) {
        return service.login(login);
    }

    @PostMapping("signup")
    public LoginResultDto signUp(@RequestBody SignUpDto dto) {
        return service.signUp(dto);
    }

}
