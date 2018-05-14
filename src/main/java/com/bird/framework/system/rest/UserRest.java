package com.bird.framework.system.rest;

import com.bird.framework.system.entity.User;
import com.bird.framework.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRest {
    @Autowired
    private UserService userService;

    @RequestMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getByUsername(@PathVariable("username") String username) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByUsername(username);
    }
}
