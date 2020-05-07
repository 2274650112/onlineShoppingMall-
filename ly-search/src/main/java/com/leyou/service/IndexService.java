package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecClient;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 20:45 2020/1/7
 */
@Service
public class IndexService {
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private GoodsRepository repository;

    public Goods buildGoods(SpuBo spuBo) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(spuBo,goods);

        String title = spuBo.getTitle();
        List<String> names = categoryClient.queryNamesByIds(Arrays.asList(spuBo.getCid1(), spuBo.getCid2(), spuBo.getCid3()));
        String s = StringUtils.join(names, " ");
        goods.setAll(title+" "+s);

        List<Sku> skus = goodsClient.querySkuBySpuId(spuBo.getId());
        List<Long> price = new ArrayList<>();

        List<Map<String,Object>> skumap=new ArrayList<>();
        for (Sku sku:skus){
            price.add(sku.getPrice());
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("skuId",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("image", StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
            skumap.add(map);
        }

        goods.setPrice(price);
        goods.setSkus(JsonUtils.serialize(skumap));
        //规格参数
        Map<String, Object> specs = getSpecs(spuBo);
        goods.setSpecs(specs);

        return goods;


    }

    //规格参数
    private Map<String, Object> getSpecs(SpuBo spuBo) {

        //key规格参数的名称，值为对应的规格参数的值（取决于商品本身）
        Map<String, Object> specs = new HashMap<>();//操作系统：android

        //1,获取到所有的可搜索的规格参数
        List<SpecParam> searchingParams = this.specClient.querySpecParam(null, spuBo.getCid3(), true,null);

        //2,循环可搜索的规格参数集合，判断通用还是特有，通用从通用规格中取值，特有从特有规格中取值

        SpuDetail spuDetail = this.goodsClient.querySpudetailByspuId(spuBo.getId());

        //由于要取值，为了方便我们把通用规格和特有规格都转换为Map
        //通用规格键值对集合map
        Map<Long, Object> genericMap = JsonUtils.nativeRead(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>() {
        });
        //特有规格键值对集合map
        Map<Long, List<String>> specialMap = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });

        //3,通用和特有的值来自于spuDetail
        searchingParams.forEach(specParam -> {
            Long id = specParam.getId();//对应取值，规格参数的id就是通用规格和特有规格保存时map的key，当key一致可以直接取值
            String name = specParam.getName();//具体的key的值

            //通用参数
            Object value = null;
            if (specParam.getGeneric()) {
                //通用参数
                value = genericMap.get(id);

                if (null != value && specParam.getNumeric()) {
                    //数值类型可能需要加分段,以及单位
                    value = this.chooseSegment(value.toString(), specParam);
                }
            } else {//特有参数
                value = specialMap.get(id);

            }
            if (null == value) {
                value = "其他";
            }
            specs.put(name, value);
        });
        return  specs;

    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {//segment:1000-2000
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();//添加单位
                }
                break;
            }
        }
        return result;
    }

    public void createIndex(Long id) {
        //查询spu
        Spu spu = goodsClient.querySpuById(id);
        SpuBo spuBo = new SpuBo();
        BeanUtils.copyProperties(spu, spuBo);
        Goods goods = this.buildGoods(spuBo);

        repository.save(goods);
    }
}
