package com.example.filecollector.web;

import com.example.filecollector.po.User;
import com.example.filecollector.service.UserService;
import com.example.filecollector.util.TokenInfo;
import com.example.filecollector.util.UserUtils;
import com.example.filecollector.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    //将Service注入Web层
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/verify")
    @ResponseBody
    public Result verify(@RequestParam String userName,@RequestParam String password, HttpServletRequest request) {
        User user = null;
        try {
            user = userService.saveUser(userName, password);
        } catch (Exception ex) {
            logger.info("密码不正确，UserService抛出异常");
            return new Result(null, "密码错误");
        }

        if (user != null) {
            //复制一份不带密码的返回
            User copyUser = UserUtils.copyUser(user);
            request.getSession().setAttribute("user", copyUser);
        }

        return new Result(null, "登陆成功");
    }
//
//    //实现注册功能
//    @PostMapping("/register")
//    public Result signUp(String name, String password){
//        userService.Insert(name, password);
//        return new Result(null, "success");
//    }
}