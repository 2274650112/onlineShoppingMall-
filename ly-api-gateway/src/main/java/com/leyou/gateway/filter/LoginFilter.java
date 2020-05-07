package com.leyou.gateway.filter;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.gateway.config.FilterProperties;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private  FilterProperties filterProperties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        //获取request上下文
        RequestContext context=RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();
        //白名单
        List<String> allowPaths = filterProperties.getAllowPaths();
        for (String url:allowPaths){
            if(requestURI.startsWith(url)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取request上下文
        RequestContext context=RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = context.getRequest();
        //解密
        try {
            //从request中获取cookie;
            String s = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
            JwtUtils.getInfoFromToken(s, jwtProperties.getPublicKey());
        } catch (Exception e) {
            //用户没有登录
            //用户登录token假的
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(403);
        }

        return null;
    }
}
