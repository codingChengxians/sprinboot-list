package com.example.category.feignclient;

import com.example.category.entity.PmsProductCategory;
import com.example.category.service.impl.PmsProductCategoryServiceImpl;
import com.example.provider.feign.CategoryServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/category")
public class CategoryFeignClient implements CategoryServiceFeignClient {
    @Autowired
    private PmsProductCategoryServiceImpl pmsProductCategoryService;

    @GetMapping(value = "/get")
    @Override
    public String getCategoryName(Long id) {
        PmsProductCategory pmsProductCategory = pmsProductCategoryService.get(id);
        if (pmsProductCategory != null) {
            return pmsProductCategory.getName();
        }
        return null;
    }
}
