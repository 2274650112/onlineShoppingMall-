package com.leyou.item.controller;

import com.leyou.common.po.PageResult;
import com.leyou.item.service.BrandService;
import com.leyou.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 19:32 2019/12/31
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> pageQuery(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                       @RequestParam(value ="rows",defaultValue = "5") Integer rows,
                                                       @RequestParam(value ="sortBy",required = false) String sortBy,
                                                       @RequestParam(value ="desc",required = false) Boolean desc,
                                                       @RequestParam(value ="key",required = false) String key

    ){
        PageResult<Brand> brandResult=brandService.pageQuery(page,rows,sortBy,desc,key);
        if(brandResult!=null&&brandResult.getItems().size()>0){
            return ResponseEntity.ok(brandResult);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping
    public ResponseEntity<Void> addBrand(Brand brand,@RequestParam("cids") List<Long> cids){

        brandService.addBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids") List<Long> cids){
        brandService.updateBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategory(@PathVariable("cid") Long cid){
        List<Brand> brands=brandService.queryBrandByCategory(cid);
        if(null!=brands&&brands.size()>0){
            return ResponseEntity.ok(brands);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
