package com.bird.framework.system.vo;


import com.bird.framework.system.entity.Tenant;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuVo implements Serializable {
    private static final long serialVersionUID = 7237412135396649076L;
    private Long id;

    private String code;

    private String name;

    private int height; // 1：菜单组，2：菜单， 3：操作（action）

    private MenuVo parent;

    private String url;

    private TenantVo tenantVo;

    private Long version;

    private String status;

    private List<MenuVo> menus = new ArrayList<>();
}
