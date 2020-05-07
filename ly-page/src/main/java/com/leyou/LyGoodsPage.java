package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 15:06 2020/1/8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LyGoodsPage {
    public static void main(String[] args) {
        SpringApplication.run(LyGoodsPage.class,args);
    }
}
