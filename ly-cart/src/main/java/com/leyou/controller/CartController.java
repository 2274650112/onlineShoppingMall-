package com.leyou.controller;

import com.leyou.pojo.Cart;
import com.leyou.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> queryCarts(){
        List<Cart> list= cartService.queryCarts();
        return ResponseEntity.ok(list);
    }

    @PutMapping("increment")
    public ResponseEntity<Void> increment(@RequestBody Cart cart){
        cartService.increment(cart);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String i){
        cartService.delete(i);
        return ResponseEntity.ok().build();
    }

}
