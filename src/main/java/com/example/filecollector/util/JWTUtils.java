package com.example.filecollector.util;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTCreator;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//
//import java.util.Calendar;
//import java.util.Map;

public class JWTUtils {
//    public static String TOKEN = "t~1o33k2en!Q@W3e4r";
//
//    /**
//     *
//     * @param map 传入payload
//     * @return token
//     */
//    public static String getToken(Map<String, String> map) {
//        JWTCreator.Builder builder = JWT.create();
//        //添加token
//        map.forEach(builder::withClaim);
//        //过期时间,5天后过期
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_WEEK, 5);
//        //添加到token
//        builder.withExpiresAt(calendar.getTime());
//        //签名，返回
//        return builder.sign(Algorithm.HMAC256(TOKEN)).toString();
//    }
//
//    /**
//     * 验证token
//     */
//
//    public static void verify(String token) {
//        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(TOKEN);
//    }
//
//    /**
//     * @param token 令牌z
//     * @return 获取token中payload
//     */
//    public static DecodedJWT getToken(String token){
//        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
//    }
}
