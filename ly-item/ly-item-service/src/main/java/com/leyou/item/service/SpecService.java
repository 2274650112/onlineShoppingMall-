package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 12:00 2020/1/3
 */
public interface SpecService {
    List<SpecGroup> querySpecGroups(Long id);

    List<SpecParam> querySpecParam(Long gid, Long cid, Boolean searching, Boolean generic);

    void saveSpecGroup(SpecGroup specGroup);

    void saveSpecParam(SpecParam specParam);

    void deleteSpecGroupById(Long id);

    void deleteSpecParamById(Long id);

    void updateSpecGroup(SpecGroup specGroup);

    void updateSpecParam(SpecParam specParam);
}
