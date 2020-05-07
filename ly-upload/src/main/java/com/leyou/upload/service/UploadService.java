package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 16:44 2020/1/2
 */
public interface UploadService {
    String uploadImage(MultipartFile file);
}
