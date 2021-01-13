package com.wry.controller;


import com.wry.common.result.Result;
import com.wry.model.dto.RoleDTO;
import com.wry.model.entity.Role;
import com.wry.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public Result<List<RoleDTO>> queryRoleList() {
        return Result.success(roleService.queryAllRole());
    }

    @PostMapping
    public Result create(@RequestBody Role role) {
        roleService.create(role);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Role role) {
        roleService.updateNotNull(role);
        return Result.success();
    }

    @DeleteMapping("{id}")
    public Result deleteByIds(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return Result.success();
    }

}
