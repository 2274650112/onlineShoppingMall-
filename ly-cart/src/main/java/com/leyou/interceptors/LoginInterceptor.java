package com.leyou.interceptors;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.config.JwtProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private JwtProperties jwtProperties;

    private static final ThreadLocal<UserInfo> tl=new ThreadLocal<>();

    public LoginInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties=jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从cookie获取值
        String s = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        if(StringUtils.isBlank(s)){
            //未登录
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        try {
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(s, jwtProperties.getPublicKey());
            tl.set(infoFromToken);
            return true;
        }catch (Exception e){
            //未登录
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //
        tl.remove();

    }

    public static UserInfo getUserInfo(){
        return  tl.get();
    }
}
