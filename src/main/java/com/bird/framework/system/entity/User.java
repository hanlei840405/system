package com.bird.framework.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "system_user")
public class User implements Serializable {

    private static final long serialVersionUID = 7505708934034027983L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "users")
    private Set<Role> roles = new HashSet<>(0);
}
