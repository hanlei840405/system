package com.bird.framework.system;

import com.bird.framework.system.entity.User;
import com.bird.framework.system.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories
public class SystemApplication {

    public static void main(String[] args) {
        ApplicationContext atx = SpringApplication.run(SystemApplication.class, args);
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername("admin");
        user.setPassword(encoder.encode("12345"));
        UserService userService = atx.getBean(UserService.class);
        userService.save(user);
    }
}
