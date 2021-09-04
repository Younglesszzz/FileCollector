package com.example.filecollector.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class SessionConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截整个项目,在interceptor中对login和session中user存在的情况放行
        registry.addInterceptor(new SessionInterceptor())
                .addPathPatterns("/");
    }
}
