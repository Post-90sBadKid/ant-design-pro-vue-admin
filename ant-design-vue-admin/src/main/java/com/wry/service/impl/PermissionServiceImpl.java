package com.wry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wry.common.constant.Constants;
import com.wry.mapper.PermissionMapper;
import com.wry.model.dto.PermissionTreeDTO;
import com.wry.model.entity.Permission;
import com.wry.service.PermissionService;
import com.wry.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPermission(Permission permission) {
        if (permission.getParentId() == Constants.PERMISSION_ROOT_ID) {
            permission.setParentIds("0/");
        } else {
            Permission parent = permissionMapper.selectById(permission.getParentId());
            permission.setParentIds(parent.makeSelfAsParentIds());
        }
        permission.setStatus(1);
        permissionMapper.insert(permission);
    }

    @Override
    public Set<String> queryPermissionTree(Long... permissionIds) {
        Set<String> permissions = new HashSet<>();
        List<Permission> permissionsList = permissionMapper.selectBatchIds(Arrays.asList(permissionIds));
        for (Permission permission : permissionsList) {
            if (StringUtils.isEmpty(permission.getPermission())) {
                continue;
            }
            permissions.add(permission.getPermission());
        }
        return permissions;
    }

    @Override
    public List<Permission> queryPermissionsByOrder() {
        QueryWrapper wrapper = new QueryWrapper<Permission>().orderByAsc("sort");
        return permissionMapper.selectList(wrapper);
    }

    @Override
    public List<PermissionTreeDTO> queryPermissionTree() {
        QueryWrapper wrapper = new QueryWrapper<Permission>().orderByAsc("sort");
        List<Permission> allPermissions = permissionMapper.selectList(wrapper);
        List<Permission> permissions = allPermissions.stream()
                .collect(Collectors.toList());
        return getPermissionTree(permissions, Constants.PERMISSION_ROOT_ID);
    }

    @Override
    public List<Permission> queryPermissions(Set<String> permissionIds) {
        QueryWrapper wrapper = new QueryWrapper<Permission>().orderByAsc("sort");
        wrapper.in("id", permissionIds);
        return permissionMapper.selectList(wrapper);
    }

    @Override
    public void updateNotNull(Permission permission) {
        permissionMapper.updateById(permission);
    }

    @Override
    public void deleteById(Long id) {
        permissionMapper.deleteById(id);
    }

    private List<PermissionTreeDTO> getPermissionTree(List<Permission> list, Long parentId) {
        List<PermissionTreeDTO> permissionTree = list.stream()
                .filter(p -> p.getParentId().equals(parentId))
                .map(PermissionTreeDTO::new)
                .collect(Collectors.toList());
        if (permissionTree.isEmpty()) {
            return Collections.emptyList();
        }
        for (PermissionTreeDTO permission : permissionTree) {
            permission.setChildren(getPermissionTree(list, permission.getId()));
        }
        return permissionTree;
    }

}
