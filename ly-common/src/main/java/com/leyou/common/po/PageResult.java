package com.leyou.common.po;

import lombok.Data;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 19:40 2019/12/31
 */
@Data
public class PageResult<T> {
    private Long total;//共有多少条数据
    private Long totalPage;//共有多少页
    private List<T> items;//每页显示的数据

    public PageResult() {
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}
