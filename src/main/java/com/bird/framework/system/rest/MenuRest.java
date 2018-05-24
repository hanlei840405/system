package com.bird.framework.system.rest;

import com.bird.framework.system.service.MenuService;
import com.bird.framework.system.service.RoleService;
import com.bird.framework.system.service.UserService;
import com.bird.framework.system.vo.MenuVo;
import com.bird.framework.system.vo.RoleVo;
import com.bird.framework.system.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuRest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/all/{pageNo}/{pageSize}")
    public Page<MenuVo> all(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize, Long tenantId, Long roleId) {
        return menuService.page(tenantId, roleId, pageNo, pageSize);
    }

    @RequestMapping("/menu")
    public List<Map<String, Object>> menu() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo userVo = userService.getByUsername(principal);
        List<Map<String, Object>> responses = new ArrayList<>();
        if (userVo != null) {
            List<RoleVo> roleVos = roleService.findByUserId(userVo.getId());
            for (RoleVo roleVo : roleVos) {
                Map<String, Object> response = new HashMap<>();
                List<MenuVo> menuGroups = menuService.findMenuGroup(roleVo.getId());
                for (MenuVo menuVo : menuGroups) { // 加载二级菜单
                    response.put("header", menuVo);
                    List<MenuVo> menuList = menuService.findMenu(menuVo.getId(), roleVo.getId());
                    response.put("list", menuList);
                    responses.add(response);
                }
            }
        }
        return responses;
    }

    @RequestMapping("/group")
    public List<MenuVo> group() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo userVo = userService.getByUsername(principal);
        List<MenuVo> menus = new ArrayList<>();
        if (userVo != null) {
            List<RoleVo> roleVos = roleService.findByUserId(userVo.getId());
            for (RoleVo roleVo : roleVos) {
                List<MenuVo> menuGroups = menuService.findMenuGroup(roleVo.getId());
                for (MenuVo menuVo : menuGroups) { // 加载二级菜单
                    List<MenuVo> menuList = menuService.findMenu(menuVo.getId(), roleVo.getId());
                    menuVo.getMenus().addAll(menuList);
                }
                menus.addAll(menuGroups);
            }
        }
        return menus;
    }
}
