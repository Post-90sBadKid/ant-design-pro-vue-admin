package com.wry.model.vo;

import lombok.Data;


/**
 * 匹配前端路由
 */
@Data
public class ServicePermissionVO {

    private Integer id;
    private Integer parentId;
    private String name;
    private String redirect;
    private String component;
    private Meta meta;

    @Data
    public static class Meta {
        private String icon;
        private Boolean show;
        private Boolean keepAlive;
        private Boolean hideChildren;
        private String title;
    }
}
