package com.wry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wry.model.dto.LoginDTO;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 登录处理，因为Security默认使用的是表单登录，
 * 此处修改为 JSON 登录
 * </p>
 *
 * @author wangruiyu
 * @since 2020/7/17
 */

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 默认的登录匹配路径
     */
    private static String DEFAULT_FILTER_PROCESSES_URL = "/login";

    protected LoginAuthenticationFilter() {
        super(DEFAULT_FILTER_PROCESSES_URL);
    }

    public static void setDefaultFilterProcessesUrl(String defaultFilterProcessesUrl) {
        DEFAULT_FILTER_PROCESSES_URL = defaultFilterProcessesUrl;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //判断请求方式是否是POST
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("请求方式不支持: " + request.getMethod());
        }
        //判断参数是否是JSON 格式
        if ((!request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) &&
                (!request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE))) {
            throw new AuthenticationServiceException("ContentType格式不支持！ ");
        }
        //获取请求体参数
        LoginDTO loginDTO = null;
        try {
            loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("非法的参数格式");
        }
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        username = (username != null) ? username : "";
        username = username.trim();
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
