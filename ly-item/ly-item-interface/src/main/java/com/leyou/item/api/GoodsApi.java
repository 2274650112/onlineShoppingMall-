package com.leyou.item.api;

import com.leyou.common.po.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 17:44 2020/1/7
 */
public interface GoodsApi {
    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuByPage(@RequestParam(value = "key",required = false) String key,
                                            @RequestParam(value = "saleable",required = false) Boolean saleable,
                                            @RequestParam(value = "page",defaultValue = "1") Integer page,
                                            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    );

    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpudetailByspuId(@PathVariable("spuId") Long id);

    @GetMapping("sku/list")
    public List<Sku> querySkuBySpuId(@RequestParam("id") Long spuid);

    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id") Long spuId);

}
