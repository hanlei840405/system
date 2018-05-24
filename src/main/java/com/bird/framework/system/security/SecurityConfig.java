package com.bird.framework.system.security;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private static String REALM = "BIRD_REALM";
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//    @Autowired
//    private BirdSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;
//
////    @Autowired
////    private SessionCsrfTokenRepository sessionCsrfTokenRepository;
//
//    @Autowired
//    private BirdAuthenticationFailureHandler myAuthenticationFailureHandler;
//    @Autowired
//    private BirdUserDetailService birdUserDetailService;
//
//    @Value("${auth.token: bird}")
//    String tokenName;
//    @Value("${auth.secret: bird}")
//    String tokenSecret;

    //    ////这个函数主要说明登陆框的样式，地址等等（有默认登陆框）
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().disable()
////                .authorizeRequests()
////                .anyRequest().authenticated()
////                .and().httpBasic().realmName(REALM).authenticationEntryPoint(restAuthenticationEntryPoint)
////                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
//
//        http
//                //disable using the session to store login information
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                //set our custom security context repository
//                .securityContext()
//                .securityContextRepository(securityContextRepository())
//                .and()
//                //set csrf token repository to use a cookie and no session
//                .csrf()
//                .csrfTokenRepository(new CookieCsrfTokenRepository())
//                .and()
//                .authorizeRequests()
//                .anyRequest().authenticated();
//    }
//
    ///这个函数主要说明需要认证的用户，密码，以及权限
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(birdUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
//
//    @Bean
//    public JWTSecurityContextRepository securityContextRepository() {
//        return new JWTSecurityContextRepository(birdUserDetailService, tokenName, tokenSecret);
//
//    }


    @Autowired
    private BirdUserDetailService birdUserDetailService;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login", "/state").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager(), redissonClient))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), redissonClient));
    }
}
