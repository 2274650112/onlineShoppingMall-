package com.leyou.item.service;

import com.leyou.item.bo.SpuBo;
import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 17:08 2020/1/3
 */
public interface GoodsService {
    PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows);

    void saveGoods(SpuBo spuBo);

    SpuDetail querySpudetailByspuId(Long id);

    List<Sku> querySkuBySpuId(Long spuid);

    void updateGoods(SpuBo spuBo);

    Spu querySpuById(Long spuId);
}
