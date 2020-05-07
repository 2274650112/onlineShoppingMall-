package com.leyou.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Mr.Xue
 * @Description:
 * @Date: Created in 20:12 2020/1/7
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
