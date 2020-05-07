package com.leyou.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 20:11 2020/1/7
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {

}
