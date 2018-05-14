package com.bird.framework.system.security;

import com.bird.framework.system.entity.Role;
import com.bird.framework.system.entity.User;
import com.bird.framework.system.service.RoleService;
import com.bird.framework.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BirdUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }
        String password = user.getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = roleService.findByUserId(user.getTenant().getId(), user.getId());
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getCode())));

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(s, password, authorities);
        return userDetails;
    }
}
