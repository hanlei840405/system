package com.bird.framework.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "system_organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = -7469790334918223334L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private String path;

    private boolean leaf;

    @ManyToOne
    private Tenant tenant;

    @ManyToOne
    private Organization organization;

    @Version
    private Long version;

    private String status;
}
