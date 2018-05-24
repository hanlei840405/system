package com.bird.framework.system.repository;

import com.bird.framework.system.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long>, QuerydslPredicateExecutor<Organization> {

    Organization findByCode(String code);

    List<Organization> findByParentId(Long parentId);

    List<Organization> findByNameStartingWith(String name);

    List<Organization> findByPathStartingWith(String path);
}
