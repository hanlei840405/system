package com.bird.framework.system.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TenantVo implements Serializable {

    private static final long serialVersionUID = 1391933054119918086L;
    private Long id;

    private String code;

    private String name;

    private String legalPerson;

    private String legalPersonId;

    private String legalPersonMobile;

    private String status;
}
