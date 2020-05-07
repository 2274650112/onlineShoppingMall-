package com.leyou.item.controller;

import com.leyou.item.bo.SpuBo;
import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.item.service.GoodsService;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 17:08 2020/1/3
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


    //?key=&saleable=true&page=1&rows=5
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(@RequestParam(value = "key",required = false) String key,
                                                            @RequestParam(value = "saleable",required = false) Boolean saleable,
                                                            @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    ){
        PageResult<SpuBo> pageResult=goodsService.querySpuByPage(key,saleable,page,rows);
        if(null!=pageResult&&pageResult.getItems().size()>0){
            return ResponseEntity.ok(pageResult);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpudetailByspuId(@PathVariable("spuId") Long id){
        SpuDetail spuDetail=goodsService.querySpudetailByspuId(id);
        if(null!=spuDetail){
            return ResponseEntity.ok(spuDetail);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //sku/list?id=182
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuid){
        List<Sku> skus=goodsService.querySkuBySpuId(spuid);
        if(null!=skus&&skus.size()>0){
            return ResponseEntity.ok(skus);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long spuId){
        Spu spu=goodsService.querySpuById(spuId);
        if(spu==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(spu);
    }

}
