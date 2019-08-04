package com.ypc.spring.customcache.service;

import com.ypc.spring.customcache.entity.User;

import java.util.List;

/**
 * @Author: ypcfly
 * @Date: 19-7-27 20:55
 * @Description:
 */
public interface UserService {
    List<User> findAll();

    User queryByName(String name);
}
