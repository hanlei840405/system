package com.bird.framework.system.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemRest {

    @RequestMapping("/state")
    public boolean state() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
