package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description: 规格组
 * @Date: Created in 11:39 2020/1/3
 */
@Data
@Table(name = "tb_spec_group")
public class SpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private String name;

    @Transient
    private List<SpecParam> specParams;
}
