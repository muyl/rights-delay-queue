package com.yxqy.web;

import com.yxqy.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 拓仲(牟云龙) on 2020/2/15
 */
@RestController
public class UserController {

    @GetMapping("/param")
    public Object param(String uid, String name) {
        return uid;
    }

    @GetMapping("/bean")
    private Object gets(User user) {
        return user;
    }
}
