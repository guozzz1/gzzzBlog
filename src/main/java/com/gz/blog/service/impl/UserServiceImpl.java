package com.gz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.blog.common.ErrorCode;
import com.gz.blog.domain.User;
import com.gz.blog.domain.dto.UserDto;
import com.gz.blog.exception.BusinessException;
import com.gz.blog.service.UserService;
import com.gz.blog.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gz.blog.common.ErrorCode.NULL_ERROR;
import static com.gz.blog.common.ErrorCode.PARAMS_ERROR;

/**
* @author 果汁汁汁
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-14 14:54:58
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    UserMapper userMapper;
    /**
     * 用户注册具体实现
     * @param userDto
     */
    public void Register(UserDto userDto){
        String userAccount = userDto.getUserAccount();
        String userPassword = userDto.getUserPassword();
        String checkPassword = userDto.getCheckPassword();

        //校验数据是否为空
        if(StringUtils.isAnyEmpty(userAccount,userPassword,checkPassword)){
            throw  new BusinessException(PARAMS_ERROR,"请把信息填完整");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(PARAMS_ERROR,"用户名过短");
        }

        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(PARAMS_ERROR);
        }

        // 账户不包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 如果包含非法字符，则返回
        if (matcher.find()) {
            throw new BusinessException(PARAMS_ERROR,"密码不能包含特殊字符");
        }

        // 密码和校验密码不相等
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(PARAMS_ERROR,"密码不一致");
        }

        //判断用户名是否已注册
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userDto.getUserAccount());
        User user1 = this.getOne(queryWrapper);
        if(!(user1 == null))
        {
            //用户名已经被注册
            throw new BusinessException( PARAMS_ERROR, "用户名已注册");
        }
        //用户名没有被注册过,现在注册
        else {
            //把用用户密码用MD5加密
            String password = userDto.getUserPassword();
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            userDto.setUserPassword(password);
            boolean saveResult = this.save(userDto);

            if(!saveResult){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统异常");
            }
        }
    }

    @Override
    public User Login(User user) {
        //校验用户信息
        if (StringUtils.isAnyBlank(user.getUserAccount(),user.getUserPassword())) {
            throw new BusinessException(NULL_ERROR,"账号或密码不能为空");
        }

        if (user.getUserAccount().length() < 4) {
            throw new BusinessException(PARAMS_ERROR,"账号过短");
        }

        if (user.getUserPassword().length() < 8) {
            throw new BusinessException(PARAMS_ERROR,"密码过短");
        }

        //密码MD5加密
        String password = user.getUserPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //构造条件查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, user.getUserAccount());
        User userLogin = this.getOne(queryWrapper);

        //判断数据库是否有该用户名
        if (userLogin == null) {
            //没有该用户名
           throw new BusinessException(NULL_ERROR, "该用户名不存在");
        }
        //有该用户名,进行密码校验
        if (!userLogin.getUserPassword().equals(password)) {
            //密码校验不正确
            throw new BusinessException(NULL_ERROR, "密码错误");
        }
        //密码校验成功

        return userLogin;
    }

    @Override
    public List<User> getUserListByQueryWrapper(LambdaQueryWrapper<User> queryWrapper) {
        return userMapper.selectList(queryWrapper);
    }
}




