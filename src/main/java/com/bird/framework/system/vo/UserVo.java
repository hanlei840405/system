package com.bird.framework.system.vo;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.entity.Tenant;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 7505708934034027983L;
    private Long id;

    private String username;

    private String password;

    private String nick;

    private String realName;

    private String mobile;

    private String email;

    private String birthday;

    private TenantVo tenantVo;

    private OrganizationVo organizationVo;

    private Long version;

    private String status;
}
