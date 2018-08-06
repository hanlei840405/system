package com.bird.framework.system;

import com.bird.framework.system.entity.*;
import com.bird.framework.system.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class SystemApplication {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        ApplicationContext atx = SpringApplication.run(SystemApplication.class, args);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Tenant tenant = new Tenant();
        tenant.setCode("0001");
        tenant.setLegalPerson("0001");
        tenant.setLegalPersonId("0001");
        tenant.setLegalPersonMobile("0001");
        tenant.setName("0001");
        tenant.setStatus("1");
        User user = new User();
        user.setUsername("admin");
        user.setPassword(encoder.encode("12345"));
        user.setTenant(tenant);
        user.setStatus("1");
        Role role = new Role();
        role.setCode("ADMIN");
        role.setName("超级管理员");
        role.setTenant(tenant);
        role.setStatus("1");
        Menu systemMenu = new Menu();
        systemMenu.setCode("0001");
        systemMenu.setName("系统管理");
        systemMenu.setTenant(tenant);
        systemMenu.setHeight(1);
        systemMenu.setStatus("1");
        Menu organizationMenu = new Menu();
        organizationMenu.setCode("00010001");
        organizationMenu.setName("组织机构管理");
        organizationMenu.setUrl("/system/organization");
        organizationMenu.setTenant(tenant);
        organizationMenu.setHeight(2);
        organizationMenu.setParent(systemMenu);
        organizationMenu.setStatus("1");
        Menu userMenu = new Menu();
        userMenu.setCode("00010002");
        userMenu.setName("用户管理");
        userMenu.setTenant(tenant);
        userMenu.setUrl("/system/user");
        userMenu.setHeight(2);
        userMenu.setParent(systemMenu);
        userMenu.setStatus("1");

        TenantService tenantService = atx.getBean(TenantService.class);
        tenantService.save(tenant);
        MenuService menuService = atx.getBean(MenuService.class);
        menuService.save(systemMenu);
        menuService.save(organizationMenu);
        menuService.save(userMenu);
        RoleService roleService = atx.getBean(RoleService.class);
        role.getMenus().add(systemMenu);
        role.getMenus().add(organizationMenu);
        role.getMenus().add(userMenu);
        roleService.save(role);
        user.getRoles().add(role);
        UserService userService = atx.getBean(UserService.class);
        userService.save(tenant.getId(), user);

        OrganizationService organizationService = atx.getBean(OrganizationService.class);
        Organization root = new Organization();
        root.setName("root");
        root.setLeaf(true);
        root.setTenant(tenant);
        organizationService.save(root);
        Organization organization = new Organization();
        organization.setName("京东商城");
        organization.setLeaf(true);
        organization.setTenant(tenant);
        organization.setParent(root);
        organizationService.save(organization);
        Organization organization1 = new Organization();
        organization1.setName("京东金融");
        organization1.setLeaf(true);
        organization1.setTenant(tenant);
        organization1.setParent(root);
        organizationService.save(organization1);
        Organization organization2 = new Organization();
        organization2.setName("京东物流");
        organization2.setLeaf(true);
        organization2.setTenant(tenant);
        organization2.setParent(root);
        organizationService.save(organization2);
    }
}
