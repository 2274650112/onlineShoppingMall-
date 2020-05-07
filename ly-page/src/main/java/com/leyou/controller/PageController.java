package com.leyou.controller;

import com.leyou.service.FileService;
import com.leyou.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 15:14 2020/1/8
 */
@Controller
public class PageController {
    @Autowired
    private PageService pageService;
    @Autowired
    private FileService fileService;

    @GetMapping("item/{id}.html")
    public String toPoge(@PathVariable("id") Long spuId, Model model){

        model.addAllAttributes(pageService.loadData(spuId));
        if(!fileService.exists(spuId)){
            fileService.syncCreateHtml(spuId);
        }
        return "item";
    }

}
