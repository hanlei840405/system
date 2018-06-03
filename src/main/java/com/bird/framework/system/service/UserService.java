package com.bird.framework.system.service;

import com.bird.framework.system.entity.Organization;
import com.bird.framework.system.entity.QUser;
import com.bird.framework.system.entity.Tenant;
import com.bird.framework.system.entity.User;
import com.bird.framework.system.repository.OrganizationRepo;
import com.bird.framework.system.repository.TenantRepo;
import com.bird.framework.system.repository.UserRepo;
import com.bird.framework.system.vo.OrganizationVo;
import com.bird.framework.system.vo.TenantVo;
import com.bird.framework.system.vo.UserVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrganizationRepo organizationRepo;
    @Autowired
    private TenantRepo tenantRepo;

    public User get(Long id) {
        return userRepo.getOne(id);
    }

    public UserVo getByUsername(String username) {
        User user = userRepo.findByUsername(username);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    public User getByNick(String nick) {
        return userRepo.findByNick(nick);
    }

    public User get(Long tenantId, Long id) {
        return userRepo.findByTenantIdAndId(tenantId, id);
    }

    public UserVo getByUsername(Long tenantId, String username) {
        return convert2Vo(userRepo.findByTenantIdAndUsername(tenantId, username));
    }

    public User getByNick(Long tenantId, String nick) {
        return userRepo.findByTenantIdAndNick(tenantId, nick);
    }

    public List<User> findByEmail(Long tenantId, String email) {
        return userRepo.findByTenantIdAndEmailStartingWith(tenantId, email);
    }

    public List<User> findByMobile(Long tenantId, String mobile) {
        return userRepo.findByTenantIdAndMobileStartingWith(tenantId, mobile);
    }

    public List<User> findByOrganization(Long tenantId, Long organizationId) {
        return userRepo.findByTenantIdAndOrganizationId(tenantId, organizationId);
    }

    public Page<UserVo> page(String payload, int pageNo, int pageSize, Long tenantId) {
        QUser qUser = QUser.user;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserVo> page;
        Page<User> userPage;
        if (!StringUtils.isEmpty(payload)) {
            BooleanExpression booleanExpression = qUser.username.like(payload);
            userPage = userRepo.findAll(booleanExpression, pageable);
        } else {
            userPage = userRepo.findAll(pageable);
        }
        page = userPage.map(this::convert2Vo);
        return page;
    }

    @Transactional
    public User save(Long tenantId, User user) {

        // 查找所在组织机构
        Organization organization = null;
        if (user.getOrganization() != null) {
            organization = organizationRepo.getOne(user.getOrganization().getId());
        }

        if (user.getId() == null) {
            // 处理新增用户逻辑
            user.setOrganization(organization);
            user.setStatus("1");
            Tenant tenant = null;
            if (tenantId != null) {
                tenant = tenantRepo.getOne(tenantId);
            }
            user.setTenant(tenant);
            return userRepo.save(user);
        } else {
            // 更新用户逻辑,无需更新所属租户信息
            User exist;
            if (tenantId != null) {
                exist = userRepo.findByTenantIdAndUsername(tenantId, user.getUsername());
            } else {
                exist = userRepo.findByUsername(user.getUsername());
            }
            BeanUtils.copyProperties(user, exist, "id", "tenant", "organization", "version");
            exist.setOrganization(organization);
            return userRepo.save(exist);
        }
    }

    private UserVo convert2Vo(User user) {
        if (user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        if (user.getOrganization() != null) {
            OrganizationVo organizationVo = new OrganizationVo();
            BeanUtils.copyProperties(user.getOrganization(), organizationVo);
            userVo.setOrganizationVo(organizationVo);
        }
        if (user.getTenant() != null) {
            TenantVo tenantVo = new TenantVo();
            BeanUtils.copyProperties(user.getTenant(), tenantVo);
            userVo.setTenantVo(tenantVo);
        }
        return userVo;
    }
}
