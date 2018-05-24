package com.bird.framework.system.vo;


import com.bird.framework.system.entity.Tenant;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleVo implements Serializable {

    private static final long serialVersionUID = -6814348886102398772L;
    private Long id;

    private String code;

    private String name;

    private Long version;

    private String status;

    private TenantVo tenantVo;
}
