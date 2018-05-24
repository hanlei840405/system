package com.bird.framework.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "system_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1248747431705007255L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private int height; // 1：菜单组，2：菜单， 3：操作（action）

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu parent;

    private String url;

    @ManyToOne
    private Tenant tenant;

    @Version
    private Long version;

    private String status;

    @ManyToMany(mappedBy = "menus")
    private Set<Role> roles = new HashSet<>(0);
}
