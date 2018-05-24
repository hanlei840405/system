package com.bird.framework.system.vo;

import com.bird.framework.system.entity.Tenant;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrganizationVo implements Serializable {

    private static final long serialVersionUID = 6084172340344955892L;
    private Long id;

    private String code;

    private String name;

    private String path;

    private boolean leaf;

    private TenantVo tenantVo;

    private OrganizationVo parent;

    private Long version;

    private String status;
}
