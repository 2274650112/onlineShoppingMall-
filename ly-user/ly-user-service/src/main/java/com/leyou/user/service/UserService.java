package com.leyou.user.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    static final String KEY_PREFIX="user:code:phone";

    public Boolean check(String data, Integer type) {
        //1.用户名 2 手机
        User user=new User();
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
        }
       return this.userMapper.selectCount(user)!=1;
    }

    public Boolean sendVerifyCode(String phone) {
        //产生验证码
        String s = NumberUtils.generateCode(5);
        //把验证码发入redis
        stringRedisTemplate.opsForValue().set(KEY_PREFIX+phone, s,5, TimeUnit.MINUTES);

        return true;
    }

    public Boolean createUser(User user, String code) {
        String s=this.stringRedisTemplate.opsForValue().get(KEY_PREFIX+user.getPhone());
        if(null==s){
            return false;
        }
        //收到数据验证码填错
        if(!code.equals(s)){
            return false;
        }
        //验证输入的用户名和手机号是否已注册
        if(!check(user.getUsername(),1) || !check(user.getPhone(),2)){
            return false;
        }
        //生成盐
        String salt = CodecUtils.generateSalt();
        //加密
        String newPwd = CodecUtils.md5Hex(user.getPassword(), salt);
        user.setPassword(newPwd);
        user.setSalt(salt);
        user.setCreated(new Date());


        boolean flag=this.userMapper.insertSelective(user)==1;
        if(flag){
            //Redis删除验证码
            this.stringRedisTemplate.delete(KEY_PREFIX+user.getPhone());

        }
        return flag;
    }

    public User queryUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        //根据用户名查询查询用户
        User user1 = this.userMapper.selectOne(user);
        if(null==user1){
           return null;
        }
        //取出盐
        String salt = user1.getSalt();
        //加密
        String newPwd =   CodecUtils.md5Hex(password, salt);
        if(!user1.getPassword().equals(newPwd)){
            return null;
        }
        return user1;
    }
}
