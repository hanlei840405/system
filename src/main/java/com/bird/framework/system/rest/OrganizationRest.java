package com.bird.framework.system.rest;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationRest {

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/id/{id}")
    public Organization get(@PathVariable("id") Long id) {
        return organizationService.get(id);
    }
}