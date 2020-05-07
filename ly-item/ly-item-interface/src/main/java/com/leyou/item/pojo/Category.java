package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Mr.Xue
 * @Description: 商品分类
 * @Date: Created in 16:36 2019/12/31
 */
@Data
@Table(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer parentId;
    private Integer isParent;
    private Long sort;
}
