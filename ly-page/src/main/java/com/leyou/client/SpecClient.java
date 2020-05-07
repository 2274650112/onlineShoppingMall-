package com.leyou.client;

import com.leyou.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 20:13 2020/1/7
 */
@FeignClient("item-service")
public interface SpecClient extends SpecApi {
}
