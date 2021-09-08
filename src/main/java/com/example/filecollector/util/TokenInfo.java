package com.example.filecollector.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.filecollector.po.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class TokenInfo {
//    public static Long getUserId(HttpServletRequest request) throws JWTVerificationException {
//        String token = request.getHeader("token");
//        DecodedJWT decodedJWT = JWTUtils.getToken(token);
//
//        Long userId = Long.parseLong(decodedJWT.getClaim("userId").asString());
//        return userId;
//    }
//
//    /**
//     * @param request 获得当前token
//     * @return 得到当前用户昵称
//     * @throws JWTVerificationException JWT鉴权错误
//     */
//    public static String getCustomNickname(HttpServletRequest request) throws JWTVerificationException {
//        String token = request.getHeader("token");
//        DecodedJWT decodedJWT = JWTUtils.getToken(token);
//        //注意！！！这里登陆时转化为token的map中是什么数据类型，取出来就得是什么类型！！不能直接asLong!
//        String nickname = decodedJWT.getClaim("nickname").asString();
//        return nickname;
//    }
//    /**
//     * @param userName 当前用户名
//     * @return token
//     */
//    public static String postToken(String userName) {
//        Map<String, String> map = new HashMap<>(5);
//
//        //把这些字段放在请求头里 其他东西在需要的时候可以另外返回
//        //注意！！！这里放进去map是什么数据类型，取出来就得是什么类型！！
//        map.put("name", userName);
//        String token = JWTUtils.getToken(map);
//
//        return token;
//    }
}
