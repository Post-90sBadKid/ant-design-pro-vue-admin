package com.wry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wry.common.result.RestResultStatus;
import com.wry.common.result.Result;
import com.wry.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * SpringSecurity的配置类
 * </p>
 *
 * @author wangruiyu
 * @since 2020/7/17
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Resource
    private UserService userService;


    @Bean
    LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        //使用web服务授权管理
        LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter();
        loginFilter.setFilterProcessesUrl("/auth/login");
        loginFilter.setAuthenticationManager(authenticationManager());

        //登录成功
        loginFilter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
            //给前端返回token
            String token = JwtUtil.sign(authentication.getName(), String.valueOf(System.currentTimeMillis()));
            Map map = new HashMap<>();
            map.put("token", token);
            map.put("role", authentication.getAuthorities());

            resp.setContentType("application/json;charset=utf8");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(new ObjectMapper().writeValueAsString(Result.success(map)));
            printWriter.flush();
            printWriter.close();
        });
        //登录失败
        loginFilter.setAuthenticationFailureHandler((req, resp, exception) -> {
            resp.setContentType("application/json;charset=utf8");
            PrintWriter out = resp.getWriter();
            Result result = null;
            if (exception instanceof AuthenticationServiceException) {
                result = Result.failure(RestResultStatus.PARAM_ERROR,exception.getMessage());
            } else if (exception instanceof LockedException) {
                result = Result.failure(RestResultStatus.ACCOUNT_LOCKED);
            } else if (exception instanceof CredentialsExpiredException) {
                result = Result.failure(RestResultStatus.PASSWORD_EXPIRED);
            } else if (exception instanceof AccountExpiredException) {
                result = Result.failure(RestResultStatus.ACCOUNT_EXPIRED);
            } else if (exception instanceof DisabledException) {
                result = Result.failure(RestResultStatus.ACCOUNT_NON_ENABLED);
            } else if (exception instanceof BadCredentialsException) {
                result = Result.failure(RestResultStatus.ACCOUNT_OR_PASSWORD_ERROR);
            }
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
            out.close();
        });


        return loginFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web)  {

        web.ignoring().antMatchers(
                "/js/**",
                "/css/**",
                "/doc.html",
                "/webjars/**",
                "/src/**",
                "/swagger-resources/**",
                "/v2/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(userService,super.authenticationManager()))
                //退出
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                //退出成功
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf8");
                    PrintWriter printWriter = resp.getWriter();
                    printWriter.write(new ObjectMapper().writeValueAsString(Result.success()));
                    printWriter.flush();
                    printWriter.close();
                })
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf8");
                    PrintWriter printWriter = resp.getWriter();
                    printWriter.write(new ObjectMapper().writeValueAsString(Result.failure(RestResultStatus.AUTHENTICATION_ERROR)));
                    printWriter.flush();
                    printWriter.close();
                })
                .accessDeniedHandler((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf8");
                    PrintWriter printWriter = resp.getWriter();
                    printWriter.write(new ObjectMapper().writeValueAsString(Result.failure(RestResultStatus.AUTHENTIZATION_ERROR)));
                    printWriter.flush();
                    printWriter.close();
                })
                .and()  // 前后端分离是无状态的，不用session了，直接禁用。
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
