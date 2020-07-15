package com.kyaw.jobsearch.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String email;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Role role;
    @JsonIgnore
    private boolean deleted;

    public enum Role {
        Admin, Candidate, Employer
    }
}
