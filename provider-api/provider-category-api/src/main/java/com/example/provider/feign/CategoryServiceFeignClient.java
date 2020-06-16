package com.example.provider.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface CategoryServiceFeignClient {

    @GetMapping(value = "/api/category/get")
    String getCategoryName(@RequestParam(value = "id") Long id);
}
