package com.bird.framework.system.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class ResourceVo implements Serializable {

    private static final long serialVersionUID = 947102556835339597L;
    private Long id;

    private String code;

    private String name;

    private TenantVo tenantVo;

    private Long version;

    private String status;
}
