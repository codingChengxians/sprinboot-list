package com.example.product.feignclient.api;

import com.example.product.feignclient.api.fallback.CategoryFallback;
import com.example.provider.feign.CategoryServiceFeignClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cloud-category",fallback = CategoryFallback.class)
public interface CategoryServiceFeign extends CategoryServiceFeignClient {

}