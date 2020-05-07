package com.leyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 16:27 2020/1/8
 */
@Service
public class FileService {
    @Value("${ly.thymeleaf.destPath}")
    private String destPath;

    @Autowired
    private PageService pageService;
    @Autowired
    private TemplateEngine templateEngine;

    public boolean exists(Long spuId) {
        File file=new File(destPath);
        if(!file.exists()){
            //创建文件夹
            file.mkdirs();
        }

        return new File(file,spuId+".html").exists();
    }

    public void syncCreateHtml(Long spuId) {
        //创建上下问对象
        Context context = new Context();
        //放入数据
        Map<String, Object> stringObjectMap = pageService.loadData(spuId);
        context.setVariables(stringObjectMap);
        //创建文件对象
        File file = new File(destPath, spuId + ".html");

        try {
            //打印流
            PrintWriter printWriter=new PrintWriter(file,"utf-8");
            //产生静态文件
            templateEngine.process("item",context,printWriter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void deleteHtml(Long id) {
        File file = new File(destPath, id + ".html");
        file.deleteOnExit();
    }
}
