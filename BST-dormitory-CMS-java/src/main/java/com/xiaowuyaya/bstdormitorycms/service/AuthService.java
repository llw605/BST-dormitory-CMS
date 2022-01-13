package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.User;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;

public interface AuthService {

    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult getInfo(String token);
}
