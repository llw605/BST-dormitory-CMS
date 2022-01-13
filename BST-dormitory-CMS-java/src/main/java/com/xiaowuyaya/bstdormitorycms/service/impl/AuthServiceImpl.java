package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaowuyaya.bstdormitorycms.entity.LoginUser;
import com.xiaowuyaya.bstdormitorycms.entity.User;
import com.xiaowuyaya.bstdormitorycms.entity.UserRole;
import com.xiaowuyaya.bstdormitorycms.mapper.UserMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.UserRoleMapper;
import com.xiaowuyaya.bstdormitorycms.service.AuthService;
import com.xiaowuyaya.bstdormitorycms.util.JwtUtil;
import com.xiaowuyaya.bstdormitorycms.util.RedisCache;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("登录失败");
            log.warn("登入失败，未找到Authentication");
            return ResponseResult.fail("登入失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", loginUser.getUser().getUserName());
        jsonObject.put("role",loginUser.getUser().getUserRole());

        String jwt = JwtUtil.createJWT(jsonObject.toJSONString());
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt); //{username, role}

        //把完整的用户信息存入redis  userid作为key     login-admin: obj
        redisCache.setCacheObject("login-"+loginUser.getUser().getUserName(), loginUser);

        return new ResponseResult(20000,"登录成功",map);
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userName = loginUser.getUser().getUserName();
        //删除redis中的值
        redisCache.deleteObject("login-"+userName);
        log.info("删除redis中的缓存");
        return new ResponseResult(20000,"注销成功");
    }

    @Override
    public ResponseResult getInfo(String token) {
        String username = "undefind";

        try {
            Claims claims = JwtUtil.parseJWT(token);
            username = (String) JSONObject.parseObject(claims.getSubject()).get("username");
        } catch (Exception e) {
            log.error("非法token",e);
            return ResponseResult.fail("非法token");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);

        User user = userMapper.selectOne(queryWrapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", user.getNickName());
        jsonObject.put("avatar", user.getAvatar());

        List<String> roles = new ArrayList<>();
        roles.add(userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("id",user.getUserRole())).getRole());
        jsonObject.put("roles", roles);

        return ResponseResult.success(jsonObject);

    }
}
