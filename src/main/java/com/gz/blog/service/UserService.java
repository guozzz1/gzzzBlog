package com.gz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gz.blog.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.blog.domain.dto.UserDto;

import java.util.List;

/**
* @author 果汁汁汁
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-14 14:54:58
*/
public interface UserService extends IService<User> {
    //注册
    public void Register(UserDto userDto);

    //登录
    public User Login(User user);

    //查询
    List<User> getUserListByQueryWrapper(LambdaQueryWrapper<User> queryWrapper);
}
