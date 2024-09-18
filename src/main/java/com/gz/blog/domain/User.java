package com.gz.blog.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@Data
public class User implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String userAccount;

    /**
     *  Long id，String userAccount，String username，Object gender,Integer userRole,Integer userStatus,Date createdAt, Date updatedAt
     */
    private String userPassword;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private Object gender;

    /**
     * 
     */
    private Integer userRole;

    /**
     * 
     */
    private Integer userStatus;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String avatarurl;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private Integer isDelete;

    /**
     * 
     */
    private Long planetCode;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;

    private static final long serialVersionUID = 1L;
}