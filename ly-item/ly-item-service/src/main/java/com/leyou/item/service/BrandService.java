package com.leyou.item.service;

import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 19:33 2019/12/31
 */
public interface BrandService {
    PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    void addBrand(Brand brand, List<Long> cids);

    void updateBrand(Brand brand, List<Long> cids);

    List<Brand> queryBrandByCategory(Long cid);
}
