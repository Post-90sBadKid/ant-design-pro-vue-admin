package com.wry.model.dto;

import com.wry.model.entity.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel
public class PermissionTreeDTO {
    @ApiModelProperty("编号")
    private Long id;
    @ApiModelProperty("资源名称")
    private String name;
    @ApiModelProperty("资源类型")
    private Integer type;
    @ApiModelProperty("权限字符串")
    private String permission;
    @ApiModelProperty("父编号")
    private Long parentId;
    @ApiModelProperty("父编号列表")
    private String parentIds;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("配置")
    private String config;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("排序")
    private Long sort;

    private List<PermissionTreeDTO> children;

    public PermissionTreeDTO(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.type = permission.getType();
        this.permission = permission.getPermission();
        this.parentId = permission.getParentId();
        this.parentIds = permission.getParentIds();
        this.icon = permission.getIcon();
        this.config = permission.getConfig();
        this.status = permission.getStatus();
        this.sort = permission.getSort();
    }

}
