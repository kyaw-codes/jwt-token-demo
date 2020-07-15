package com.kyaw.jobsearch.config;

import com.kyaw.jobsearch.model.BaseRepositoryImpl;
import com.kyaw.jobsearch.model.dto.SignUpDto;
import com.kyaw.jobsearch.model.entity.Account;
import com.kyaw.jobsearch.model.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.kyaw.jobsearch.model.repo"},
        repositoryBaseClass = BaseRepositoryImpl.class
)
public class RepositoryConfig {

    @Autowired
    private AccountService service;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            if (service.count() == 0) {
                SignUpDto admin = new SignUpDto();
                admin.setEmail("Admin");
                admin.setPassword("Admin");
                admin.setRole(Account.Role.Admin);
                admin.setName("Admin User");

                service.signUp(admin);
            }
        };
    }
}
