package com.wry.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@TableName(value = "sys_user")
@Data
public class User implements UserDetails {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nikename;
    /**
     * 拥有的角色列表
     */
    private String roleIds;
    /**
     * 是否启用
     */
    @Getter(value = AccessLevel.NONE)
    private Boolean enabled;
    /**
     * 帐户是否未过期
     */
    @Getter(value = AccessLevel.NONE)
    private Boolean accountNonExpired;
    /**
     * 帐户是否未锁定
     */
    @Getter(value = AccessLevel.NONE)
    private Boolean accountNonLocked;
    /**
     * 凭据是否未过期
     */
    @Getter(value = AccessLevel.NONE)
    private Boolean credentialsNonExpired;

    /**
     * 用户的角色列表
     */
    @TableField(exist = false)
    private Collection<String> roleList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(roleList).orElse(new HashSet<>()).stream().map(role ->
                new SimpleGrantedAuthority(role)

        ).collect(Collectors.toList());

    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
