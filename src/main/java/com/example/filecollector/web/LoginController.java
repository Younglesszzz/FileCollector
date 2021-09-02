package com.example.filecollector.web;

import com.example.filecollector.po.User;
import com.example.filecollector.service.UserService;
import com.example.filecollector.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    //将Service注入Web层
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(String name, String password){
        User user = userService.LoginIn(name, password);
        if(user !=null){
            return new Result(null, "success");
        }else {
            return new Result(null, "error");
        }
    }
//
//    //实现注册功能
//    @PostMapping("/register")
//    public Result signUp(String name, String password){
//        userService.Insert(name, password);
//        return new Result(null, "success");
//    }
}