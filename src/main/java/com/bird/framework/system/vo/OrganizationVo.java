package com.bird.framework.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class OrganizationVo implements Serializable {

    private static final long serialVersionUID = 6084172340344955892L;
    private Long id;

    private String code;

    private String name;

    private String path;

    private boolean leaf;

    private OrganizationVo parent;

    private TenantVo tenantVo;

    private Long version;

    private String status;
}
