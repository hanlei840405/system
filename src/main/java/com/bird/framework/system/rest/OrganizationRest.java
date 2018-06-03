package com.bird.framework.system.rest;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.service.OrganizationService;
import com.bird.framework.system.service.UserService;
import com.bird.framework.system.vo.OrganizationVo;
import com.bird.framework.system.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationRest {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;

    @RequestMapping("/id/{id}")
    public OrganizationVo id(@PathVariable("id") Long id) {
        return organizationService.get(id);
    }

    @RequestMapping("/tree/{id}")
    public List<OrganizationVo> tree(@PathVariable("id") Long id) {
        return organizationService.findByParentId(id);
    }

    @RequestMapping("/page")
    public Page<OrganizationVo> page(Long parentId, String name,  Integer pageNo,  Integer pageSize) {
        pageNo = pageNo > 0 ? pageNo - 1: pageNo;
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo userVo = userService.getByUsername(principal);
        return organizationService.page(parentId, name, pageNo, pageSize, userVo.getTenantVo() == null ? null : userVo.getTenantVo().getId());
    }

    @PostMapping("/save")
    public int save(@RequestBody Organization organization) {
        organizationService.save(organization);
        return HttpStatus.OK.value();
    }
}
