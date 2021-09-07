package com.example.filecollector.web;

import com.example.filecollector.po.User;
import com.example.filecollector.service.UserService;
import com.example.filecollector.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody User user) {
        if (userService.update(user) == null)
            return new Result(null, "修改失败");
        return new Result(null, "修改成功");
    }
}
