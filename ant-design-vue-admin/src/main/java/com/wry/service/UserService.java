package com.wry.service;


import com.wry.common.exception.BusinessException;
import com.wry.model.entity.User;
import com.wry.model.page.PageWrapper;
import com.wry.model.query.UserQuery;
import com.wry.model.vo.ServicePermissionVO;
import com.wry.model.vo.UserPageVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    /**
     * 创建用户
     *
     * @param user
     */
    void createUser(User user) throws BusinessException;

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);

    /**
     * 根据用户名查找其角色
     *
     * @param username
     * @return
     */
    Set<String> queryRoles(String username);

    /**
     * 根据用户名查找其权限
     *
     * @param username 用户名
     * @param code  权限类型
     * @return
     */
    Set<String> queryPermissions(String username, int code);

    /**
     * 根据用户的角色得到菜单
     *
     * @param username
     * @return
     */
    List<ServicePermissionVO> queryMenus(String username);

    /**
     * 用户名查询用户
     *
     * @param username
     * @return
     */
    User queryByUsername(String username);

    /**
     * 分页查询
     *
     * @param userQuery
     * @return
     */
    PageWrapper<UserPageVO> queryUserPage(UserQuery userQuery);

    /**
     * 更新
     *
     * @param user
     */
    void updateNotNull(User user);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Long id);
}
