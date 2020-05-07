package com.leyou.item;

import com.leyou.LySearchService;
import com.leyou.client.GoodsClient;
import com.leyou.common.po.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 20:14 2020/1/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class IndexTest {
    @Autowired
    private ElasticsearchTemplate esTemplate;
    @Autowired
    private GoodsRepository repository;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private IndexService indexService;

    @Test
    public void test(){
        //创建索引
        esTemplate.createIndex(Goods.class);
        // 配置映射
        esTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData(){
        int page=1;
        while (true){
            //使用feignClient调用商品微服务
            PageResult<SpuBo> spuBoPageResult = goodsClient.querySpuByPage(null, null, page, 50);

            if(spuBoPageResult==null){
                break;
            }
            page++;
            //获取商品集合
            List<SpuBo> items = spuBoPageResult.getItems();

            List<Goods> goodsList = new ArrayList<>();

            for (SpuBo spuBo:items){
                //spubo-->goods
                Goods goods=indexService.buildGoods(spuBo);
                goodsList.add(goods);
            }
            repository.saveAll(goodsList);
        }


    }

}
