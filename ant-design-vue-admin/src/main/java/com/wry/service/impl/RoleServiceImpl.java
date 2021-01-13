package com.wry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wry.mapper.RoleMapper;
import com.wry.model.entity.Permission;
import com.wry.model.entity.Role;
import com.wry.model.vo.RoleVO;
import com.wry.service.PermissionService;
import com.wry.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Set<String> queryRoles(Long... roleIds) {
        Set<String> roles = new HashSet<>();
        List<Role> roleList = roleMapper.selectBatchIds(Arrays.asList(roleIds));
        for (Role role : roleList) {
            roles.add(role.getRole());
        }
        return roles;
    }

    @Override
    public Map<String, String> queryRoleNames(Long... roleIds) {
        Map<String, String> roleMap = new HashMap<>();
        List<Role> roleList = roleMapper.selectBatchIds(Arrays.asList(roleIds));
        for (Role role : roleList) {
            roleMap.put(role.getRole(), role.getName());
        }
        return roleMap;
    }

    @Override
    public Set<String> queryPermissions(Long... roleIds) {
        return permissionService.queryPermissionTree(
                roleMapper.selectList(new QueryWrapper<Role>().in("id", Arrays.asList(roleIds))).stream().flatMap(r ->
                        Stream.of(r.getPermissionIds().split(","))
                ).map(Long::valueOf).collect(Collectors.toSet()).toArray(new Long[]{})
        );
    }

    @Override
    public List<Permission> queryPermissionsByRoleIds(Long... roleIds) {
        List<Role> roleList = roleMapper.selectBatchIds(Arrays.asList(roleIds));
        Set<String> permissionIds = new HashSet<>();
        roleList.forEach(item -> {
            permissionIds.addAll(Arrays.stream(item.getPermissionIds().split(",")).collect(Collectors.toSet()));
        });
        return permissionService.queryPermissions(permissionIds);
    }

    @Override
    public List<RoleVO> queryAllRole() {
        return roleMapper.selectList(new QueryWrapper<>()).stream()
                .map(RoleVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Role role) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role", role.getRole());
        Role r = roleMapper.selectOne(wrapper);
        if (Objects.isNull(r)) {
            roleMapper.insert(role);
        } else {
            role.setId(r.getId());
            roleMapper.updateById(role);
        }
    }

    @Override
    public void updateNotNull(Role role) {
        roleMapper.updateById(role);
    }

    @Override
    public void deleteById(Long id) {
        roleMapper.deleteById(id);
    }

}
