package com.gz.blog.mapper;

import com.gz.blog.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 果汁汁汁
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-09-14 14:54:58
* @Entity com.gz.blog.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




