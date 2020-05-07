package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("data") String data,@PathVariable("type") Integer type){
        Boolean mark=userService.check(data,type);
        if(!mark){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(mark);
    }

    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone){
        Boolean bol=userService.sendVerifyCode(phone);
        if(null!=bol && bol){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("register")
    public ResponseEntity<Void> createUser(User user,@RequestParam("code") String code){
        Boolean mark=userService.createUser(user,code);
        if(null!=mark&&mark){
            return  ResponseEntity.ok().build();
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,@RequestParam("password") String password){
        User user=userService.queryUser(username,password);
        if(null!=user){
            return  ResponseEntity.ok(user);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
