package com.bird.framework.system.repository;

import com.bird.framework.system.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Long>, QuerydslPredicateExecutor<Resource> {
    Resource findByCode(String code);

    List<Resource> findByRolesId(Long roleId);
}
