package com.bird.framework.system.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class RoleVo implements Serializable {

    private static final long serialVersionUID = -6814348886102398772L;
    private Long id;

    private String code;

    private String name;

    private TenantVo tenantVo;

    private Long version;

    private String status;
}
