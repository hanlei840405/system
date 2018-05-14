package com.bird.framework.system.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "system_tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1391933054119918086L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private String legalPerson;

    private String legalPersonId;

    private String legalPersonMobile;

    private String status;
}
