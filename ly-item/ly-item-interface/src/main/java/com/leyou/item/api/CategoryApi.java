package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 17:52 2020/1/7
 */
public interface CategoryApi {

    @GetMapping("category/list")
    public List<Category>queryByParentId(@RequestParam("pid") Integer pid);

    @GetMapping("category/bid/{bid}")
    public List<Category> queryByBrandId(@PathVariable("bid") Long id);


    @GetMapping("category/names")
    public List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);
}
