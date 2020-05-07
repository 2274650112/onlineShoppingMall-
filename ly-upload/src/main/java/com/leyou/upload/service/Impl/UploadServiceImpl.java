package com.leyou.upload.service.Impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 16:44 2020/1/2
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private FastFileStorageClient client;
    @Override
    public String uploadImage(MultipartFile file){
//        File f = new File("C:\\blbjava01\\upload");
//
//        if(!f.exists()){
//            f.mkdirs();
//        }
//        //保存图片
//        try {
//            file.transferTo(new File(f,file.getOriginalFilename()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
        String url=null;
        String filename = file.getOriginalFilename();
        //获取文件后缀
        String ext = StringUtils.substringAfter(filename, ".");

        try {
            StorePath storePath = this.client.uploadFile(file.getInputStream(), file.getSize(), ext, null);
            url="http://image.leyou.com/"+storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
