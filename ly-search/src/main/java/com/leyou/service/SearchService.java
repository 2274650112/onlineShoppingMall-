package com.leyou.service;

import com.leyou.client.GoodsClient;
import com.leyou.common.po.PageResult;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.repository.GoodsRepository;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 22:11 2020/1/7
 */
@Service
public class SearchService {
    @Autowired
    private GoodsRepository repository;

    public PageResult<Goods> search(SearchRequest searchRequest) {
        //用户搜索关键字
        String key = searchRequest.getKey();
        //第几页
        Integer page = searchRequest.getPage();
        // 构建查询条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id","skus","subTitle"}, null));


        //查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("all",key).operator(Operator.AND));
        //分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page-1,searchRequest.getSize()));
        //搜索
        Page<Goods> search = repository.search(nativeSearchQueryBuilder.build());

        return new PageResult<>(search.getTotalElements(),new Long(search.getTotalPages()),search.getContent());

    }
}
