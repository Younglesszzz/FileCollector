package com.example.filecollector.dao;


import com.example.filecollector.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndPassword(String name, String password);
    //多个参数要加@Param修饰
    //增加，可以实现注册功能

}