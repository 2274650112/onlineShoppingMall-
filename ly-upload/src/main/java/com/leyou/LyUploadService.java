package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 16:36 2020/1/2
 */

@SpringBootApplication
@EnableDiscoveryClient
public class LyUploadService {
    public static void main(String[] args) {
        SpringApplication.run(LyUploadService.class,args);
    }
}
