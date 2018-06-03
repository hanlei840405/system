package com.bird.framework.system.service;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.entity.QOrganization;
import com.bird.framework.system.repository.OrganizationRepo;
import com.bird.framework.system.vo.OrganizationVo;
import com.bird.framework.system.vo.TenantVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private RedisSequenceService redisSequenceService;

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

    @Transactional
    public Organization save(Organization organization) {
        String parentPath;
        if (organization.getParent() != null) {
            Organization parent = organizationRepo.getOne(organization.getParent().getId());
            organization.setParent(parent);
            parentPath = parent.getPath();
            // 更新父节点的leaf状态
            parent.setLeaf(false);
            organizationRepo.save(parent);
        } else {
            organization.setParent(null);
            parentPath = "";
        }

        if (organization.getId() == null) {
            // 新增，leaf节点设置为true, path根据上级的path追加
            organization.setLeaf(true);
            organization.setCode(redisSequenceService.generate("organization"));
            organization.setStatus("1");
            organization.setPath(parentPath + "/" + organization.getCode());
            return organizationRepo.save(organization);
        }else {
            Organization exist = organizationRepo.getOne(organization.getId());
            exist.setName(organization.getName());
            exist.setParent(organization.getParent());
            String oldPath = exist.getPath();
            // 更新子节点path属性
            List<Organization> organizations = organizationRepo.findByPathStartingWith(exist.getPath() + "/");
            organizations.parallelStream().forEach(item -> item.setPath(item.getPath().replace(oldPath, parentPath + "/" + exist.getCode())));
            organizationRepo.saveAll(organizations);
            exist.setPath(parentPath + "/" + exist.getCode());
            return organizationRepo.save(exist);
        }
    }

    private OrganizationVo convert2Vo(Organization organization) {
        if (organization == null) {
            return null;
        }
        OrganizationVo organizationVo = new OrganizationVo();
        BeanUtils.copyProperties(organization, organizationVo);
        if (organization.getParent() != null) {
            OrganizationVo parentVo = new OrganizationVo();
            BeanUtils.copyProperties(organization.getParent(), parentVo);
            organizationVo.setParent(parentVo);
        }
        if (organization.getTenant() != null) {
            TenantVo tenantVo = new TenantVo();
            BeanUtils.copyProperties(organization.getTenant(), tenantVo);
            organizationVo.setTenantVo(tenantVo);
        }
        return organizationVo;
    }
}
