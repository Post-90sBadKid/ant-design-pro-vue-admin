package com.wry.controller;

import com.wry.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wangruiyu
 * @since 2021/1/9
 */
@RestController
@RequestMapping("/hello")
@Api(tags = "测试")
public class HelloController {
    @PostMapping("/c")
    public Result c() {
        return Result.success("c");
    }

    @GetMapping("/b")
    public Result b() {
        return Result.success("b");
    }

    @GetMapping("/a")
    @PreAuthorize("hasRole('admin')")
    public Result a() {
        return Result.success("a");
    }

    @GetMapping("/d")
    @PreAuthorize("hasAuthority('admin')")
    public Result d() {
        return Result.success("d");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:view')")
    public Result list() {
        return Result.success("list");
    }

    @GetMapping("/cetate")
    @PreAuthorize("hasAuthority('user:create')")
    public Result view() {
        return Result.success("cetate");
    }
}
