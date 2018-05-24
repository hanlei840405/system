package com.bird.framework.system.service;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.entity.QOrganization;
import com.bird.framework.system.repository.OrganizationRepo;
import com.bird.framework.system.vo.OrganizationVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    public OrganizationVo get(Long id) {
        Organization organization = organizationRepo.getOne(id);
        return convert2Vo(organization);
    }

    public OrganizationVo getByCode(String code) {
        Organization organization = organizationRepo.findByCode(code);
        return convert2Vo(organization);
    }

    public List<OrganizationVo> findByParentId(Long parentId) {
        List<Organization> organizations = organizationRepo.findByParentId(parentId);
        List<OrganizationVo> organizationVos = new ArrayList<>();
        organizations.forEach(organization -> organizationVos.add(convert2Vo(organization)));
        return organizationVos;
    }

    public List<OrganizationVo> findByPath(String path) {
        List<Organization> organizations = organizationRepo.findByPathStartingWith(path);
        List<OrganizationVo> organizationVos = new ArrayList<>();
        organizations.forEach(organization -> organizationVos.add(convert2Vo(organization)));
        return organizationVos;
    }

    public List<OrganizationVo> findByName(String name) {
        List<Organization> organizations = organizationRepo.findByNameStartingWith(name);
        List<OrganizationVo> organizationVos = new ArrayList<>();
        organizations.forEach(organization -> organizationVos.add(convert2Vo(organization)));
        return organizationVos;
    }

    public Page<OrganizationVo> page(Long parentId, String name, int pageNo, int pageSize, Long tenantId) {
        QOrganization qOrganization = QOrganization.organization;
        BooleanExpression booleanExpression = qOrganization.parent.id.eq(parentId);
        if (!StringUtils.isEmpty(name)) {
            booleanExpression.and(qOrganization.name.like(name));
        }
        if (tenantId != null) {
            booleanExpression.and(qOrganization.tenant.id.eq(tenantId));
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<OrganizationVo> page;
        if (booleanExpression != null) {
            Page<Organization> organizations = organizationRepo.findAll(booleanExpression, pageable);
            page = organizations.map(this::convert2Vo);
        } else {
            Page<Organization> organizations = organizationRepo.findAll(pageable);
            page = organizations.map(this::convert2Vo);
        }
        return page;
    }

    public Organization save(Organization organization) {
        return organizationRepo.save(organization);
    }

    private OrganizationVo convert2Vo(Organization organization) {
        OrganizationVo organizationVo = new OrganizationVo();
        BeanUtils.copyProperties(organization, organizationVo);
        return organizationVo;
    }
}
