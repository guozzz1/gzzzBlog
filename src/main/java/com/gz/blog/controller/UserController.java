package com.gz.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gz.blog.common.R;
import com.gz.blog.domain.User;
import com.gz.blog.domain.dto.UserDto;
import com.gz.blog.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

import static com.gz.blog.common.ErrorCode.NOT_LOGIN;
import static com.gz.blog.common.ErrorCode.NO_AUTH;

/**
 * @Author: guozzz
 * @Date: 2024/09/01/12:16
 * @Description:
 */
//@Transactional
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:7000",allowCredentials = "true")
@Slf4j
@Api(tags = "用户管理接口")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 用户注册
     *
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public R<UserDto> userRegister(@RequestBody UserDto userDto) {
        userService.Register(userDto);
        log.info("注册成功");
        return R.success(userDto);
    }
    /**
     * 用户登录
     *
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> userLogin(HttpServletRequest request, @RequestBody User user) {
        User userLogin = userService.Login(user);
        request.getSession().setAttribute("userLogin", userLogin.getId());

        Long userLoginId =(Long) request.getSession().getAttribute("userLogin");
        log.info("登录成功,id为："+userLoginId);
        return R.success(userLogin);

    }

    /**
     * 获取当前登录用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public R <List> getCurrentUser(HttpServletRequest request) {
        //获取当前登录id
          Long id =(Long) request.getSession().getAttribute("userLogin");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        List<User> userList = userService.list(queryWrapper);
        log.info("查询当前登录用户信息:" + userList);
        return R.success(userList);
    }

    /**
     * 管理员的搜素功能
     *
     * @param request
     * @param
     * @return
     */
    @PostMapping("/search")
    public R searchUsers(HttpServletRequest request) {
        //获取登录ID
        Long id = (Long)request.getSession().getAttribute("userLogin");
        log.info("登录id:" + id);
        if(id!=null){
            //查看当前登录用户是否为管理员
        User userLogin = userService.getById(id);
        Integer userRole = userLogin.getUserRole();
        log.info("用户角色：" + userRole);

        // 是管理员
        if (isAdmin(userRole)) {
            //根据id查询用户
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            List<User> list = userService.getUserListByQueryWrapper(queryWrapper);
            log.info("list:" + list);
            return R.success(list);
        } else {
            //不是管理员
            return R.error(NO_AUTH, "无权限");
        }
        }else {
            return R.error(NOT_LOGIN,"登录超时，请重新登录");
        }
    }



    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @PostMapping("/delete")
    public R deleteUser(@RequestBody User user) {
        userService.removeById(user);
        return R.success("删除成功");

    }

    /**
     * 用户退出
     *
     * @param request
     */
    @PostMapping("/logout")
    private void userLogout(HttpServletRequest request) {

        request.getSession().removeAttribute("userLogin");
    }

    /**
     * 信息更改
     *
     * @param user
     * @return
     */
    @PostMapping("/update")
    public R<String> updateUser(@RequestBody UserDto user) {
        //密码MD5加密
        user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes()));
        userService.updateById(user);
        return R.success("信息修改成功");
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public R<String> addUser(@RequestBody User user) {
        //密码MD5加密
        user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes()));
        userService.save(user);
        return R.success("添加成功");
    }

    private boolean isAdmin(Integer id) {
        return id == 1;
    }
}
