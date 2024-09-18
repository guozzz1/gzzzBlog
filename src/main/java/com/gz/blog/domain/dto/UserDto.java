package com.gz.blog.domain.dto;

import com.gz.blog.domain.User;
import lombok.Data;

/**
 * @Author: guozzz
 * @Date: 2024/09/14/14:45
 * @Description:
 */
@Data
public class UserDto extends User {

    private Integer current;

    private Integer pageSize;

    private String sortField;

    private String sortOrder;

    private String checkPassword;
}
