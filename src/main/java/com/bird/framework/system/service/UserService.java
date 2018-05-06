package com.bird.framework.system.service;

import com.bird.framework.system.entity.User;
import com.bird.framework.system.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User get(Long id) {
        return userRepo.getOne(id);
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User getByNick(String nick) {
        return userRepo.findByNick(nick);
    }

    public User get(Long tenantId, Long id) {
        return userRepo.findByTenantIdAndId(tenantId, id);
    }

    public User getByUsername(Long tenantId, String username) {
        return userRepo.findByTenantIdAndUsername(tenantId, username);
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

    public void save(User user) {
        userRepo.save(user);
    }
}
