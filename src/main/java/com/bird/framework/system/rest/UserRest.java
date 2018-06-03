package com.bird.framework.system.rest;

import com.bird.framework.system.entity.User;
import com.bird.framework.system.service.UserService;
import com.bird.framework.system.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRest {
    @Autowired
    private UserService userService;

    @RequestMapping("/username/{username}")
//    @PreAuthorize("hasRole('ADMIN')")
    public UserVo getByUsername(@PathVariable("username") String username) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo loginUserVo = userService.getByUsername(principal);
        if (loginUserVo.getTenantVo() != null) {
            return userService.getByUsername(loginUserVo.getTenantVo().getId(), username);
        }
        return userService.getByUsername(username);
    }

    @RequestMapping("/username/page")
    public Page<UserVo> page(String payload, Integer pageNo, Integer pageSize) {
        pageNo = pageNo > 0 ? pageNo - 1 : pageNo;
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo loginUserVo = userService.getByUsername(principal);
        return userService.page(payload, pageNo, pageSize, loginUserVo.getTenantVo() == null ? null : loginUserVo.getTenantVo().getId());
    }

    @PostMapping("/save")
    public int save(@RequestBody User user) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVo loginUserVo = userService.getByUsername(principal);
        Long userTenantId = user.getTenant() == null ? null : user.getTenant().getId();
        Long loginUserTenantId = loginUserVo.getTenantVo() == null ? null : loginUserVo.getTenantVo().getId();
        if (userTenantId != null && loginUserTenantId != null && userTenantId.equals(loginUserTenantId)) {
            userService.save(loginUserTenantId, user);
            return HttpStatus.OK.value();
        } else {
            // 登录用户的租户信息与数据所属的租户信息不想等，禁止更新
            return HttpStatus.EXPECTATION_FAILED.value();
        }
    }
}
