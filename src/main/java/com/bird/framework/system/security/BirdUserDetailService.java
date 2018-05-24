package com.bird.framework.system.security;

import com.bird.framework.system.entity.Role;
import com.bird.framework.system.entity.User;
import com.bird.framework.system.service.RoleService;
import com.bird.framework.system.service.UserService;
import com.bird.framework.system.vo.RoleVo;
import com.bird.framework.system.vo.UserVo;
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
        UserVo userVo = userService.getByUsername(s);
        if (userVo == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }
        String password = userVo.getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<RoleVo> roleVos = roleService.findByUserId(userVo.getId());
        roleVos.forEach(roleVo -> authorities.add(new SimpleGrantedAuthority(roleVo.getCode())));

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(s, password, authorities);
        return userDetails;
    }
}
