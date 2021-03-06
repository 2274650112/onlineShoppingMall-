package com.leyou.item.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 17:11 2020/1/3
 */
@Data
public class SpuBo extends Spu {
    @Transient
    private String cname;// 商品分类名称
    @Transient
    private String bname;// 品牌名称
    @Transient
    private List<Sku> skus;
    @Transient
    private SpuDetail spuDetail;
}
