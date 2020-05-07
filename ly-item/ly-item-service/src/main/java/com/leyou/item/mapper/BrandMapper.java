package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 19:33 2019/12/31
 */
public interface BrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand values(#{i},#{id})")
    void insertBrandCategory(@Param("i") Long i, @Param("id") Integer id);
    @Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteBrandCategory(@Param("id") Integer id);
    @Select("select * from tb_brand b, tb_category_brand cb where b.id=cb.brand_id and category_id=#{cid}")
    List<Brand> queryBrandByCategory(@Param("cid") Long cid);
}
