package com.wry.service;

import com.wry.model.dto.RoleDTO;
import com.wry.model.entity.Permission;
import com.wry.model.entity.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {

    /**
     * 根据角色编号得到角色标识符列表
     *
     * @param roleIds
     * @return
     */
    Set<String> queryRoles(Long... roleIds);

    /**
     * 根据角色编号得到角色名称
     *
     * @param roleIds
     * @return map => key:角色标识符，value:角色名称
     */
    Map<String, String> queryRoleNames(Long... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     *
     * @param roleIds
     * @return
     */
    Set<String> queryPermissions(Long... roleIds);

    /**
     * 根据角色编号得到权限列表
     * @param roleIds
     * @return
     */
    List<Permission> queryPermissionsByRoleIds(Long... roleIds);

    /**
     * 查询所有角色
     *
     * @return
     */
    List<RoleDTO> queryAllRole();

    /**
     * 新增
     *
     * @param role
     */
    void create(Role role);

    /**
     * 更新
     *
     * @param role
     */
    void updateNotNull(Role role);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Long id);

}
