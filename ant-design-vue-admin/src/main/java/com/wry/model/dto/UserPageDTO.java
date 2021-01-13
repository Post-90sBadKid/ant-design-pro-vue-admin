package com.wry.model.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserPageDTO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nikename;
    /**
     * 拥有的角色列表
     */
    private List<Long> roleIds;
    /**
     * 拥有的角色列表
     */
    private List<String> roleNames;
    /**
     * 帐户是否未过期
     */
    private Boolean isAccountNonExpired;
    /**
     * 帐户是否未锁定
     */
    private Boolean isAccountNonLocked;
    /**
     * 凭据是否未过期
     */
    private Boolean isCredentialsNonExpired;
}
