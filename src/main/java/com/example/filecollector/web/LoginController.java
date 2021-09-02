package com.example.filecollector.web;

import com.example.registerandlogindemo.po.User;
import com.example.registerandlogindemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    //将Service注入Web层
    @Autowired
    private UserService userService;


    @PostMapping("/loginIn")
    public String loginIn(String name,String password){
        User user = userService.LoginIn(name, password);
        log.info("name:{}",name);
        log.info("password:{}",password);
        if(user !=null){
            return "success";
        }else {
            return "error";
        }
    }

    @RequestMapping("/signup")
    public String disp(){
        return "signup";
    }

    //实现注册功能
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String signUp(String name,String password){
        userService.Insert(name, password);
        return "success";
    }
}