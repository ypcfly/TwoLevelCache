package com.ypc.spring.customcache.controller;

import com.ypc.spring.customcache.entity.User;
import com.ypc.spring.customcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ypcfly
 * @Date: 19-8-1 20:36
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity selectUser(@PathVariable("userName") String userName) {
        User user = userService.queryByName(userName);
        return ResponseEntity.ok(user);
    }
}
