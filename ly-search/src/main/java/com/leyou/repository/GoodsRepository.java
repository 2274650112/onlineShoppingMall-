package com.leyou.repository;

import com.leyou.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 20:30 2020/1/7
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long>{

}
