package com.ypc.spring.customcache.service.impl;

import com.ypc.spring.customcache.entity.User;
import com.ypc.spring.customcache.repository.UserRepository;
import com.ypc.spring.customcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ypcfly
 * @Date: 19-7-27 20:55
 * @Description: service
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * cache名称应该和配置文件定义的container名称一致
     */
    private static final String CACHE_NAME = "caffeine_cache";

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Cacheable(value = CACHE_NAME,key="#name")
    @Override
    public User queryByName(String name) {

        return userRepository.findByUsername(name);
    }
}
