package com.bird.framework.system.rest;

import com.bird.framework.system.entity.User;
import com.bird.framework.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRest {
    @Autowired
    private UserService userService;

    @RequestMapping("/username/{username}")
    public User getByUsername(@PathVariable("username") String username) {
        return userService.getByUsername(username);
    }
}
