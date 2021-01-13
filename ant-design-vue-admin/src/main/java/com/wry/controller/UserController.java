package com.wry.controller;


import com.wry.common.result.RestResultStatus;
import com.wry.common.result.Result;
import com.wry.model.dto.UserBatchDeleteDTO;
import com.wry.model.entity.User;
import com.wry.model.page.PageWrapper;
import com.wry.model.query.UserQuery;
import com.wry.model.vo.UserPageVO;
import com.wry.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;


@Api(tags = "用户")
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("分页查询")
    @GetMapping
    public Result<PageWrapper<UserPageVO>> queryUserList(UserQuery userQuery) {
        return Result.success(userService.queryUserPage(userQuery));
    }

    @ApiOperation("新增")
    @PostMapping
    public Result create(@RequestBody User user) {
        if (user.isEnabled()) {
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
        } else {
            user.setAccountNonExpired(false);
            user.setAccountNonLocked(false);
            user.setCredentialsNonExpired(false);
        }
        userService.createUser(user);
        return Result.success();
    }

    @ApiOperation("修改")
    @PutMapping
    public Result update(@RequestBody User user) {
        // 如果想要禁用自己
        if (isSelf(user.getId()) && !user.isEnabled()) {
            return Result.failure(RestResultStatus.FAILED_DEL_OWN);
        }
        if (user.isEnabled()) {
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
        } else {
            user.setAccountNonExpired(false);
            user.setAccountNonLocked(false);
            user.setCredentialsNonExpired(false);
        }
        userService.updateNotNull(user);
        return Result.success();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public Result deleteBatchByIds(@RequestBody @Validated UserBatchDeleteDTO userBatchDeleteDTO) {
        Long[] ids = userBatchDeleteDTO.getIds();
        if (isSelf(ids)) {
            return Result.failure(RestResultStatus.FAILED_DEL_OWN);
        }
        Arrays.stream(ids).forEach(userService::deleteById);
        return Result.success();
    }

    private boolean isSelf(Long... ids) {
        // 当前用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.queryByUsername(username);
        return Arrays.stream(ids).anyMatch(id -> id.equals(user.getId()));
    }

    @PostMapping("{id}/change/password")
    public Result changePassword(@PathVariable("id") Long id, @NotNull String newPassword) {
        userService.changePassword(id, newPassword);
        return Result.success();
    }

}
