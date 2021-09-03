package com.example.filecollector.service;

import com.example.filecollector.dao.UserRepository;
import com.example.filecollector.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    //将dao层属性注入service层
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User saveUser(String name, String password) throws Exception{
        User user = userRepository.findByNameAndPassword(name, password);
        if (user == null) {
            user = new User(name, password);
            userRepository.save(user);
        } else {
            if (password != user.getPassword()) {
                throw new Exception("密码错误");
            }
        }
        return user;
    }

}