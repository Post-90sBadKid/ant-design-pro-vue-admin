package com.wry.security;

import com.wry.common.exception.BusinessException;
import com.wry.common.result.RestResultStatus;
import com.wry.model.entity.User;
import com.wry.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public JwtAuthenticationFilter(UserService userService,AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.userService=userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JwtUtil.ACCESS_TOKEN);
        //如果请求头中没有Authorization信息,则给用户提示 请登录
        //如果需要不支持匿名用户的请求没带token，这里放过也没问题，
        // 因为SecurityContext中没有认证信息，后面会被权限控制模块拦截
        if (!StringUtils.hasLength(token)) {
            chain.doFilter(request, response);
            return;
        }
        //如果请求头中有token,则进行解析，并且设置认证信息
        if (JwtUtil.verify(token)) {
            String username = JwtUtil.getClaim(token);
            Set<String> roles = userService.queryRoles(username);
            User user = new User();
            user.setUsername(username);
            user.setRoleList(roles);
            UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authResult);
            chain.doFilter(request, response);
            return;
        }
        throw new BusinessException(RestResultStatus.ACCOUNT_EXPIRED);
    }



}
