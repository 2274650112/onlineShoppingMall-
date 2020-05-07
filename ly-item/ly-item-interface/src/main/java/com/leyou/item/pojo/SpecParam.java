package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Mr.Xue
 * @Description: 规格参数
 * @Date: Created in 11:42 2020/1/3
 */
@Data
@Table(name = "tb_spec_param")
public class SpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}
