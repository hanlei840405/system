package com.bird.framework.system.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "system_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -6814348886102398772L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @Version
    private Long version;

    private String status;

    @ManyToOne
    private Tenant tenant;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>(0);

    @ManyToMany
    @JoinTable(name = "sys_role_menu",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private Set<Menu> menus = new HashSet<>(0);

    @ManyToMany
    @JoinTable(name = "sys_role_resource",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_id")})
    private Set<Resource> resources = new HashSet<>(0);
}
