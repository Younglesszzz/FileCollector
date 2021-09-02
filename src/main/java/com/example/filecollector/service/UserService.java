package com.example.filecollector.service;

import com.example.registerandlogindemo.dao.UserRepository;
import com.example.registerandlogindemo.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //将dao层属性注入service层
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User LoginIn(String name, String password) {
        User user = userRepository.findByNameAndPassword(name, password);
        return user;
    }

    public void Insert(String name,String password){
        logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        User user = new User(name, password);
        userRepository.save(user);
    }
}