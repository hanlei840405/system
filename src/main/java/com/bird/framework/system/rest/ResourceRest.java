package com.bird.framework.system.rest;

import com.bird.framework.system.entity.Resource;
import com.bird.framework.system.service.ResourceService;
import com.bird.framework.system.service.RoleService;
import com.bird.framework.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceRest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/all/{pageNo}/{pageSize}")
    public Page<Resource> all(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize, Long tenantId, Long roleId) {
        return resourceService.page(tenantId, roleId, pageNo, pageSize);
    }
}
