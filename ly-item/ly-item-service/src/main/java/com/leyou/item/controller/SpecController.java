package com.leyou.item.controller;

import com.leyou.item.service.SpecService;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 11:59 2020/1/3
 */
@RestController
@RequestMapping("spec")
public class SpecController {
    @Autowired
    private SpecService specService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroups(@PathVariable("cid")Long id){
        List<SpecGroup> groupList=specService.querySpecGroups(id);
        if(groupList!=null&&groupList.size()>0){
            return ResponseEntity.ok(groupList);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParam(@RequestParam(value = "gid",required = false)Long gid,
                                                          @RequestParam(value = "cid",required = false)Long cid,
                                                          @RequestParam(value = "searching",required = false)Boolean searching,
                                                          @RequestParam(value = "generic",required = false)Boolean generic
    ){
        List<SpecParam> paramList=specService.querySpecParam(gid,cid,searching,generic);
        if(paramList!=null&&paramList.size()>0){
            return ResponseEntity.ok(paramList);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //新增分组
    @PostMapping("group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup){
        specService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //新增参数
    @PostMapping("param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParam specParam){

        specService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @DeleteMapping("group/{gid}")
    public ResponseEntity<Void> deleteSpecGroupById(@PathVariable("gid") Long id){
        specService.deleteSpecGroupById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("param/{pid}")
    public ResponseEntity<Void> deleteSpecParamById(@PathVariable("pid") Long id){
        specService.deleteSpecParamById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup){
        specService.updateSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam){
        specService.updateSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
