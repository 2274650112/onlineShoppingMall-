package com.leyou.item.service.Impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.service.CategoryService;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 16:57 2019/12/31
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> queryByParentId(Integer pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    @Override
    public List<Category> queryByBrandId(Long id) {
        return categoryMapper.queryByBrandId(id);
    }

    @Override
    public List<String> queryNamesByIds(List<Long> asList) {
        List<Category> categories = categoryMapper.selectByIdList(asList);
        List<String> names=new ArrayList<String>();
        for (Category cg:categories){
            names.add(cg.getName());
        }
        return names;
    }
}
