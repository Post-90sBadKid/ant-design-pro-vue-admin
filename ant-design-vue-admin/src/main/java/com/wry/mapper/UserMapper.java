package com.wry.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wry.model.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取单个用户
     *
     * @param username
     * @return
     */
    default User selectByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper<>().eq("username", username);
        return this.selectOne(wrapper);
    }

}
