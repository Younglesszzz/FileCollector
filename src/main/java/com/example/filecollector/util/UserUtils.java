package com.example.filecollector.util;

import com.example.filecollector.po.User;

public class UserUtils {
    public static User copyUser(User user) {
        User user1 = new User();
        user1.setId(user.getId());
        user1.setUserName(user.getUserName());
        return user1;
    }
}
