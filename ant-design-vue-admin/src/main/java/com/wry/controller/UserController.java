package com.wry.controller;


import com.wry.common.result.RestResultStatus;
import com.wry.common.result.Result;
import com.wry.model.dto.UserPageDTO;
import com.wry.model.entity.User;
import com.wry.model.query.PageWrapper;
import com.wry.model.query.UserQuery;
import com.wry.model.vo.UserBatchDeleteVO;
import com.wry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;


@RequestMapping("user")
@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<PageWrapper<UserPageDTO>> queryUserList(UserQuery userQuery) {
        return Result.success(userService.queryUserPage(userQuery));
    }

    @PostMapping
    public Result create(@RequestBody User user) {
        if (user.isEnabled()) {
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
        }else{
            user.setAccountNonExpired(false);
            user.setAccountNonLocked(false);
            user.setCredentialsNonExpired(false);
        }
        userService.createUser(user);
        return Result.success();
    }

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
        }else{
            user.setAccountNonExpired(false);
            user.setAccountNonLocked(false);
            user.setCredentialsNonExpired(false);
        }
        userService.updateNotNull(user);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteBatchByIds(@RequestBody @Validated UserBatchDeleteVO userDeleteVO) {
        Long[] ids = userDeleteVO.getIds();
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
