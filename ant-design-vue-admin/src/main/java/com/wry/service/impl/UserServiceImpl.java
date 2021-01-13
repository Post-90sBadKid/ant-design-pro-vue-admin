package com.wry.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wry.common.constant.Constants;
import com.wry.common.enums.PermissionType;
import com.wry.common.exception.BusinessException;
import com.wry.common.result.RestResultStatus;
import com.wry.mapper.UserMapper;
import com.wry.model.dto.UserPageDTO;
import com.wry.model.entity.Permission;
import com.wry.model.entity.User;
import com.wry.model.page.PageWrapper;
import com.wry.model.query.UserQuery;
import com.wry.model.vo.ServicePermissionVO;
import com.wry.service.RoleService;
import com.wry.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user) {
        User u = userMapper.selectByUsername(user.getUsername());
        if (u != null) {
            throw new BusinessException(RestResultStatus.FAILED_USER_ALREADY_EXIST);
        }
        //设置默认密码
        user.setPassword(Constants.DEFAULT_PASSWORD);
        // 加密密码
//        passwordHelper.encryptPassword(user);
        userMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(Long userId, String newPassword) {
        User user = userMapper.selectById(userId);
        user.setPassword(newPassword);
//        passwordHelper.encryptPassword(user);
        userMapper.updateById(user);
    }

    @Override
    public Set<String> queryRoles(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.queryRoles(getRoleIds(user));
    }

    @Override
    public Set<String> queryPermissions(String username, int code) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.queryPermissionsByRoleIds(getRoleIds(user))
                .stream()
                .filter(permission -> permission.getType().equals(code))
                .map(Permission::getPermission)
                .collect(Collectors.toSet());
    }

    @Override
    public List<ServicePermissionVO> queryMenus(String username) {
        User user = userMapper.selectByUsername(username);
        List<Permission> permissions = roleService.queryPermissionsByRoleIds(getRoleIds(user));
        return generateServicePermissionVO(permissions);
    }

    private List<ServicePermissionVO> generateServicePermissionVO(List<Permission> permissions) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<ServicePermissionVO> resultList = new ArrayList<>();
        ServicePermissionVO vo = null;
        for (Permission permission : permissions) {
            try {
                if (permission.getType().equals(PermissionType.MENU.getCode())) {
                    vo = mapper.readValue(permission.getConfig(), ServicePermissionVO.class);
                    vo.setId(permission.getId().intValue());
                    vo.setParentId(permission.getParentId().intValue());
                    resultList.add(vo);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new BusinessException(RestResultStatus.GENERATE_SERVICE_PERMISSION_ERROR);
            }
        }
        return resultList;
    }

    @Override
    public User queryByUsername(String username) {
        return userMapper.selectByUsername(username);
    }


    @Override
    public PageWrapper<UserPageDTO> queryUserPage(UserQuery userQuery) {
        Page<User> page = new Page<>(userQuery.getPageNo(), userQuery.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        Optional.ofNullable(userQuery.getId()).ifPresent((item) ->
                wrapper.eq(true, "id", userQuery.getId())
        );
        Optional.ofNullable(userQuery.getEnabled()).ifPresent((item) ->
                wrapper.eq("enabled", userQuery.getEnabled())
        );
        Optional.ofNullable(userQuery.getUsername()).ifPresent((item) ->
                wrapper.like(StringUtils.hasLength(userQuery.getUsername()), "username", userQuery.getUsername())
        );
        wrapper.orderBy(true, userQuery.isAsc(), Optional.ofNullable(userQuery.getSortField()).orElse("id"));
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        List<User> users = userPage.getRecords();
        List<UserPageDTO> list = new ArrayList<>();
        for (User user : users) {
            UserPageDTO userDTO = new UserPageDTO();
            BeanUtils.copyProperties(user, userDTO);
            userDTO.setRoleIds(Arrays.asList(getRoleIds(user)));
            userDTO.setRoleNames(getRoleNames(user));
            list.add(userDTO);
        }
        return new PageWrapper(page);
    }

    @Override
    public void updateNotNull(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    private List<String> getRoleNames(User user) {
        Map<String, String> roleMap = roleService.queryRoleNames(getRoleIds(user));
        return roleMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private Long[] getRoleIds(User user) {
        return Stream.of(user.getRoleIds().split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList()).toArray(new Long[0]);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        user.setRoleList(queryRoles(username));
        return user;
    }
}
