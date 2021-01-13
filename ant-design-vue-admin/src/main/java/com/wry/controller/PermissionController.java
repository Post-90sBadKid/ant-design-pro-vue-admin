package com.wry.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wry.common.result.Result;
import com.wry.model.dto.PermissionTreeDTO;
import com.wry.model.entity.Permission;
import com.wry.model.page.PageWrapper;
import com.wry.model.vo.PermissionTreeVO;
import com.wry.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "权限")
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("树状结构数据")
    @GetMapping("no-pager")
    public Result<PageWrapper<PermissionTreeVO>> queryPermissionTreeNoPager() {
        List<PermissionTreeDTO> permissionTreeDTOS = permissionService.queryPermissionTree();
        PageWrapper<PermissionTreeVO> objectPageWrapper = new PageWrapper<>();
        objectPageWrapper.setData(permissionTreeDTOS.stream().map(PermissionTreeVO::new).collect(Collectors.toList()));
        return Result.success(objectPageWrapper);
    }

    @ApiOperation("树状结构")
    @GetMapping("tree")
    public Result queryPermissionTree() {
        return Result.success(permissionService.queryPermissionTree());
    }

    @ApiOperation("修改")
    @PutMapping
    public Result updatePermission(@RequestBody Permission permission) {
        permission.setConfig(generateConfig(permission, permission.getConfig()));
        permissionService.updateNotNull(permission);
        return Result.success();
    }

    @ApiOperation("新增")
    @PostMapping
    public Result createPermission(@RequestBody Permission permission) {
        permission.setConfig(generateConfig(permission, permission.getConfig()));
        permissionService.createPermission(permission);
        return Result.success();
    }

    private String generateConfig(Permission permission, String config) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            if (config != null && !config.isEmpty()) {
                Map<String, Object> configMap = mapper.readValue(config, Map.class);
                Map<String, Object> meta = (Map<String, Object>) configMap.getOrDefault("meta", Map.class);
                meta.put("icon", permission.getIcon());
                meta.put("title", permission.getName());
                return mapper.writeValueAsString(config);
            }
        } catch (Exception e) {
        }
        return config;
    }

    @ApiOperation("删除")
    @DeleteMapping("{id}")
    public Result deletePermission(@PathVariable Long id) {
        permissionService.deleteById(id);
        return Result.success();
    }

}
