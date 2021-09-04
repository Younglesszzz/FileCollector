package com.example.filecollector.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("/login".equals(request.getRequestURI())) {
            return true;
        }

        Object object = request.getSession().getAttribute("user");
        if (null == object) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
