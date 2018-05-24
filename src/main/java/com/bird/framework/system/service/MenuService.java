package com.bird.framework.system.service;

import com.bird.framework.system.entity.Menu;
import com.bird.framework.system.entity.QMenu;
import com.bird.framework.system.repository.MenuRepo;
import com.bird.framework.system.vo.MenuVo;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MenuRepo menuRepo;

    public MenuVo get(Long id) {
        Menu menu = menuRepo.getOne(id);
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(menu, menuVo, "roles");
        return menuVo;
    }

    public List<MenuVo> findMenuGroup(Long roleId) {
        QMenu qMenu = QMenu.menu;
        JPAQuery query = new JPAQuery(entityManager);
        query.from(qMenu).distinct().where(qMenu.roles.any().id.eq(roleId).and(qMenu.height.eq(1)));
        Iterable<Menu> menus = query.fetch();
        List<MenuVo> menuVos = new ArrayList<>();
        for (Menu menu : menus) {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menu, menuVo, "roles");
            menuVos.add(menuVo);
        }
        return menuVos;
    }

    public List<MenuVo> findMenu(Long groupId, Long roleId) {
        QMenu qMenu = QMenu.menu;
        JPAQuery query = new JPAQuery(entityManager);
        query.from(qMenu).distinct().where(qMenu.roles.any().id.eq(roleId).and(qMenu.parent.id.eq(groupId)).and(qMenu.height.eq(2)));
        Iterable<Menu> menus = query.fetch();

        List<MenuVo> menuVos = new ArrayList<>();
        for (Menu menu : menus) {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menu, menuVo, "roles");
            menuVos.add(menuVo);
        }
        return menuVos;
    }

    public Menu save(Menu menu) {
        return menuRepo.save(menu);
    }

    public Page<MenuVo> page(Long tenantId, Long roleId, int pageNo, int pageSize) {
        QMenu qMenu = QMenu.menu;
        BooleanExpression booleanExpression = null;
        if (tenantId != null) {
            if (booleanExpression == null) {
                booleanExpression = qMenu.tenant.id.eq(tenantId);
            } else {
                booleanExpression.and(qMenu.tenant.id.eq(tenantId));
            }
        }
        if (roleId != null) {
            if (booleanExpression == null) {
                booleanExpression = qMenu.roles.any().id.eq(roleId);
            } else {
                booleanExpression.and(qMenu.roles.any().id.eq(roleId));
            }
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Menu> menus = menuRepo.findAll(booleanExpression, pageable);
        Page<MenuVo> menuVos = menus.map(this::convert);
        return menuVos;
    }

    private MenuVo convert(Menu menu) {
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(menu, menuVo, "roles");
        return menuVo;
    }
}
