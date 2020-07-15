package com.kyaw.jobsearch.model.dto;

import com.kyaw.jobsearch.model.entity.Account;
import lombok.Data;

@Data
public class SignUpDto {

    private String email;
    private String name;
    private String password;
    private Account.Role role;

}
