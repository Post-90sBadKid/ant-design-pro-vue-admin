package com.wry.service;

import com.wry.model.dto.PermissionTreeDTO;
import com.wry.model.entity.Permission;
import com.wry.model.vo.ServicePermissionVO;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    /**
     * 得到资源对应的权限字符串
     *
     * @param permissionIds
     * @return
     */
    Set<String> queryPermissionTree(Long... permissionIds);

    /**
     * 查询权限集合
     *
     * @return
     */
    List<Permission> queryPermissionsByOrder();

    /**
     * 查询树状结构
     *
     * @return
     */
    List<PermissionTreeDTO> queryPermissionTree();

    /**
     * 根据角色id 查询资源
     *
     * @param permissionIds
     * @return
     */
    List<Permission> queryPermissions(Set<String> permissionIds);

    /**
     * 新增
     *
     * @param permission
     */
    void createPermission(Permission permission);

    /**
     * 更新
     *
     * @param permission
     */
    void updateNotNull(Permission permission);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Long id);


}
