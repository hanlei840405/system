package com.bird.framework.system.rest;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.service.OrganizationService;
import com.bird.framework.system.vo.OrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationRest {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/id/{id}")
    public OrganizationVo id(@PathVariable("id") Long id) {
        return organizationService.get(id);
    }

    @RequestMapping("/tree/{id}")
    public List<OrganizationVo> tree(@PathVariable("id") Long id) {
        return organizationService.findByParentId(id);
    }

    @RequestMapping("/page")
    public Page<OrganizationVo> page(Long parentId, String name,  Integer pageNo,  Integer pageSize, Long tenantId) {
        pageNo = pageNo > 0 ? pageNo - 1: pageNo;
        return organizationService.page(parentId, name, pageNo, pageSize, tenantId);
    }

    @PostMapping("/save")
    public int save(@RequestBody Organization organization) {
        organizationService.save(organization);
        return HttpStatus.OK.value();
    }
}
