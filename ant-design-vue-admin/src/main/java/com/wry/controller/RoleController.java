package com.wry.controller;


import com.wry.common.result.Result;
import com.wry.model.entity.Role;
import com.wry.model.vo.RoleVO;
import com.wry.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "角色")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("查询所有")
    @GetMapping
    public Result<List<RoleVO>> queryRoleList() {
        return Result.success(roleService.queryAllRole());
    }

    @ApiOperation("新增")
    @PostMapping
    public Result create(@RequestBody Role role) {
        roleService.create(role);
        return Result.success();
    }
    @ApiOperation("修改")
    @PutMapping
    public Result update(@RequestBody Role role) {
        roleService.updateNotNull(role);
        return Result.success();
    }
    @ApiOperation("删除")
    @DeleteMapping("{id}")
    public Result deleteByIds(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return Result.success();
    }

}
