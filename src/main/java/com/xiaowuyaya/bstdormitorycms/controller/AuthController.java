package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.entity.User;
import com.xiaowuyaya.bstdormitorycms.service.AuthService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseResult login(User user){
        return authService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return authService.logout();
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(@RequestHeader("token") String token){
        return authService.getInfo(token);
    }
}
