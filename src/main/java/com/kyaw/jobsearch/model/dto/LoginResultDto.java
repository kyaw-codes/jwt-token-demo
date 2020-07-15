package com.kyaw.jobsearch.model.dto;

import com.kyaw.jobsearch.model.entity.Account;
import lombok.Data;

@Data
public class LoginResultDto {

    private String name;
    private Account.Role role;
    private boolean success;
    private String message;
}
