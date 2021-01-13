package com.wry.controller;

import com.wry.common.constant.Constants;
import com.wry.common.enums.PermissionType;
import com.wry.common.result.Result;
import com.wry.model.vo.ServicePermissionVO;
import com.wry.model.vo.UserInfoVO;
import com.wry.service.PermissionService;
import com.wry.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@Api(tags = "首页")
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户信息")
    @GetMapping("user/info")
    public Result<UserInfoVO> getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<String> role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setName(username);
        userInfoVO.setAvatar(Constants.DEFAULT_AVATAR);
        //查询按钮权限
        userInfoVO.setPermissions(userService.queryPermissions(username, PermissionType.BUTTON.getCode()));
        userInfoVO.setRole(role);
        return Result.success(userInfoVO);
    }

    @ApiOperation("菜单列表")
    @GetMapping("user/nav")
    public Result<List<ServicePermissionVO>> getUserNav() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ServicePermissionVO> list = userService.queryMenus(username);
        return Result.success(list);
    }

//    @ApiOperation("初始化数据库")
//    @GetMapping("datasource/initialize")
//    public Result initializeDatasource() {
//        DataSource dataSource = SpringUtils.getBean(DataSource.class);
//        ResourceLoader loader = new DefaultResourceLoader();
//        Resource schema = loader.getResource("classpath:schema.sql");
//        Resource data = loader.getResource("classpath:data.sql");
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schema, data);
//        populator.execute(dataSource);
//        return Result.success();
//    }

}
