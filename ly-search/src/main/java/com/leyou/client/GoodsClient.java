package com.leyou.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 20:09 2020/1/7
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
