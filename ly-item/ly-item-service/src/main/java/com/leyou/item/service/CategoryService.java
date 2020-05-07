package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 16:54 2019/12/31
 */
public interface CategoryService {
    List<Category> queryByParentId(Integer pid);

    List<Category> queryByBrandId(Long id);

    List<String> queryNamesByIds(List<Long> asList);
}
