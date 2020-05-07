package com.leyou.item.service.Impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.service.SpecService;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 12:00 2020/1/3
 */
@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;


    @Override
    public List<SpecGroup> querySpecGroups(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(id);
        List<SpecGroup> groups = specGroupMapper.select(specGroup);
        groups.forEach(t->{
            SpecParam specParam = new SpecParam();
            specParam.setGroupId(t.getId());
            t.setSpecParams(specParamMapper.select(specParam));
        });
        return groups;
    }

    @Override
    public List<SpecParam> querySpecParam(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);

        return specParamMapper.select(specParam);
    }

    @Override
    public void saveSpecGroup(SpecGroup specGroup) {

        this.specGroupMapper.insert(specGroup);
    }

    @Override
    public void saveSpecParam(SpecParam specParam) {
        this.specParamMapper.insert(specParam);
    }

    @Override
    @Transactional
    public void deleteSpecGroupById(Long id) {
        this.specGroupMapper.deleteByPrimaryKey(id);
        SpecParam specParam=new SpecParam();
        specParam.setGroupId(id);
        //删除改分组下的参数
        this.specParamMapper.delete(specParam);

    }

    @Override
    public void deleteSpecParamById(Long id) {
        this.specParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateSpecGroup(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKey(specGroup);
    }

    @Override
    public void updateSpecParam(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKey(specParam);
    }
}
