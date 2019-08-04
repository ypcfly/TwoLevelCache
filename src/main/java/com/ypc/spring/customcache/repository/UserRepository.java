package com.ypc.spring.customcache.repository;

import com.ypc.spring.customcache.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: ypcfly
 * @Date: 19-7-27 20:05
 * @Description: reactive repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String name);
}
