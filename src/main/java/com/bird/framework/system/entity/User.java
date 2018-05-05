package com.bird.framework.system.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "system_user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nick;

    private String realName;

    private String mobile;

    private String email;

    private String birthday;

    @ManyToOne
    private Tenant tenant;

    @ManyToOne
    private Organization organization;

    @Version
    private Long version;

    private String status;
}
