package com.leyou.item.controller;

import com.leyou.item.service.CategoryService;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 16:54 2019/12/31
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam("pid") Integer pid) {
        List<Category> list = categoryService.queryByParentId(pid);
        if (null != list && list.size() > 0) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long id){
        List<Category> list=categoryService.queryByBrandId(id);
        if(list!=null&&list.size()>0){

            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids") List<Long> ids){
        List<String> names = categoryService.queryNamesByIds(ids);
        if(names!=null&&names.size()>0){

            return ResponseEntity.ok(names);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
