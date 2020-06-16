package com.example.product.feignclient.api.fallback;

import com.example.product.feignclient.api.CategoryServiceFeign;
import org.springframework.stereotype.Component;

@Component
public class CategoryFallback implements CategoryServiceFeign {

    @Override
    public String getCategoryName(Long id) {
        return "sentinel：调用失败，服务被降级";
    }
}
