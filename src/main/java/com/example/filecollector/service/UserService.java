package com.example.filecollector.service;

import com.example.filecollector.dao.UserRepository;
import com.example.filecollector.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    //将dao层属性注入service层
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User saveUser(String name, String password) throws Exception{
        User found = userRepository.findByUserName(name);
        //如果数据库中连用户名都没有，那就注册一个
        if (found == null) {
            userRepository.save(new User(name, password));
        }
        //存在用户名，那就检查密码
        else {
            if (!password.equals(found.getPassword())) {
                throw new Exception("密码错误");
            }
        }
        return found;
    }

    @Transactional
    public User update(User user) {
        Optional<User> oldUser = userRepository.findById(user.getId());
        if (oldUser.isPresent()) {
            oldUser.get().setEmail(user.getEmail());
            oldUser.get().setUserName(user.getUserName());
        }
        return oldUser.orElse(null);
    }

}