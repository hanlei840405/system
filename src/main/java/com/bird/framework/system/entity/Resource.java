package com.bird.framework.system.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "system_resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 947102556835339597L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @ManyToOne
    private Tenant tenant;

    @Version
    private Long version;

    private String status;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "resources")
    private Set<Role> roles = new HashSet<>(0);
}
