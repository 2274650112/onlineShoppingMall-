package com.leyou.item.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.po.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.service.BrandService;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 19:34 2019/12/31
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //开启分页
        PageHelper.startPage(page,rows);
        Example example = new Example(Brand.class);

        if(StringUtils.isNotBlank(key)){
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name","%"+key+"%");

        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+(desc?" desc":" asc"));
        }
        Page<Brand> brandPage=(Page<Brand>)brandMapper.selectByExample(example);

        return new PageResult<Brand>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage.getResult());
    }

    @Transactional
    @Override
    public void addBrand(Brand brand, List<Long> cids) {
        brandMapper.insertSelective(brand);
        for(Long i:cids){
            brandMapper.insertBrandCategory(i,brand.getId());

        }
    }

    @Override
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        //更新tb_brand表
        brandMapper.updateByPrimaryKey(brand);
        //删除关系表
        brandMapper.deleteBrandCategory(brand.getId());

        cids.forEach(t->{
            brandMapper.insertBrandCategory(t,brand.getId());
        });
    }

    @Override
    public List<Brand> queryBrandByCategory(Long cid) {

        return brandMapper.queryBrandByCategory(cid);
    }
}
