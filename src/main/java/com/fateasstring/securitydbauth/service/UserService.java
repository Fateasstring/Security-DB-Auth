package com.fateasstring.securitydbauth.service;

import com.fateasstring.securitydbauth.bean.User;
import com.fateasstring.securitydbauth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** 执行登陆的逻辑,需要实现 UserDetailsService 接口 */

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    /** 在执行登录的过程中，loadUserByUsername这个方法将根据用户名去查找用户，
     如果用户不存在，则抛出UsernameNotFoundException异常，否则直接将查到的结果返回 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);

        if (user == null){
            /** 如果user没有找到，则抛出异常 */
            throw new UsernameNotFoundException("用户不存在");
        }

        /** 如果用户存在，则根据用户的ID查询用户的角色 */
        user.setRoles(userMapper.getUserRolesById(user.getId()));

        return user;
    }

}
