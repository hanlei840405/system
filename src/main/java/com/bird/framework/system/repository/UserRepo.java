package com.bird.framework.system.repository;

import com.bird.framework.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    User findByUsername(String username);

    User findByNick(String nick);

    User findByMobile(String mobile);

    User findByTenantIdAndId(Long tenantId, Long id);

    User findByTenantIdAndUsername(Long tenantId, String username);

    User findByTenantIdAndNick(Long tenantId, String nick);

    List<User> findByTenantIdAndEmailStartingWith(Long tenantId, String email);

    List<User> findByTenantIdAndMobileStartingWith(Long tenantId, String mobile);

    List<User> findByTenantIdAndOrganizationId(Long tenantId, Long organizationId);
}
