package com.bird.framework.system.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jesse.Han
 */
@Data
public class MenuVo implements Serializable {
    private static final long serialVersionUID = 7237412135396649076L;
    private Long id;

    private String code;

    private String name;

    /**
     * 1：菜单组，2：菜单， 3：操作（action）
     */
    private int height;

    private MenuVo parent;

    private String url;

    private Long version;

    private String status;

    private TenantVo tenantVo;

    private List<MenuVo> menus = new ArrayList<>();
}
