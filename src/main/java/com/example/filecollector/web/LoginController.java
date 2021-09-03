package com.example.filecollector.web;

import com.example.filecollector.po.User;
import com.example.filecollector.service.UserService;
import com.example.filecollector.util.TokenInfo;
import com.example.filecollector.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
public class LoginController {

    //将Service注入Web层
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(String userName, String password){
        try {
            userService.saveUser(userName, password);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(null, "密码错误");
        }

        String token = TokenInfo.postToken(userName);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);
        return new Result(hashMap, "登陆成功");
    }
//
//    //实现注册功能
//    @PostMapping("/register")
//    public Result signUp(String name, String password){
//        userService.Insert(name, password);
//        return new Result(null, "success");
//    }
}