package com.leyou.service;

import com.leyou.client.GoodsClient;
import com.leyou.client.SpecClient;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.Spu;
import com.leyou.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 15:16 2020/1/8
 */
@Service
public class PageService{
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecClient specClient;

    public Map<String, Object> loadData(Long spuId) {
        Map<String, Object> map=new HashMap<>();
        Spu spu = goodsClient.querySpuById(spuId);
        map.put("spu",spu);
        map.put("spuDetail",goodsClient.querySpudetailByspuId(spuId));
        map.put("skus",goodsClient.querySkuBySpuId(spuId));

        List<SpecParam> specParams = specClient.querySpecParam(null, spu.getCid3(), null, null);
        //map.put("specParams",specParams);
        Map<Long,Object> specMap = new HashMap<Long,Object>();
        for (SpecParam specParam:specParams){
            specMap.put(specParam.getId(),specParam.getName());
        }
        map.put("specParams",specMap);
        List<SpecGroup> specGroupList = specClient.querySpecGroups(spu.getCid3());
        map.put("specGroups",specGroupList);
        return map;
    }
}
