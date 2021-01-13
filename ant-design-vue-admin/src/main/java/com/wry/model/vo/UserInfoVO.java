package com.wry.model.vo;

import lombok.Data;

import java.util.Set;


@Data
public class UserInfoVO {
    private String name;
    private String avatar;
    private Set<String> permissions;
    private Set<String> role;

}
