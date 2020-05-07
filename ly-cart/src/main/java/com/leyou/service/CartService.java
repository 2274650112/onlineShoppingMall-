package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.auth.entity.UserInfo;
import com.leyou.common.utils.JsonUtils;
import com.leyou.interceptors.LoginInterceptor;
import com.leyou.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private  static final String PEX="ly:cart:uid:";

    public void addCart(Cart cart) {

        //获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(PEX + userInfo.getId());

        Object obj = ops.get(cart.getSkuId().toString());
        if(null!=obj){
            //首先从redis中取出，变成对象
           Cart ct=JsonUtils.nativeRead(obj.toString(),new TypeReference<Cart>(){});
           //页面cart和cart数量增加
            ct.setNum(ct.getNum()+cart.getNum());
            ops.put(ct.getSkuId().toString(), JsonUtils.serialize(ct));

        }else{
            ops.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
        }
        //放入购物车


    }

    public List<Cart> queryCarts() {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(PEX + userInfo.getId());
        List<Object> values = ops.values();

        List<Cart> carts=new ArrayList<>();

        for (Object o:values){
            //把redis
            Cart ct=JsonUtils.nativeRead(o.toString(),new TypeReference<Cart>(){});
            carts.add(ct);
        }
        return carts;
    }

    public void increment(Cart cart) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(PEX + userInfo.getId());
        //根据商品id获取值
        Object o = ops.get(cart.getSkuId().toString());

        Cart ct=JsonUtils.nativeRead(o.toString(),new TypeReference<Cart>(){});

        ct.setNum(ct.getNum()+1);
        ops.put(cart.getSkuId().toString(), JsonUtils.serialize(ct));

    }

    public void delete(String i) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(PEX + userInfo.getId());
        ops.delete(i);
    }
}
