package com.example.product.service;

import com.example.product.entity.PmsProduct;
import com.github.pagehelper.PageInfo;

/**
 * 商品信息(PmsProduct)表服务接口
 *
 * @author luok
 * @since 2020-06-02 15:11:02
 */
public interface PmsProductService {
    PageInfo<PmsProduct> list(String filter, Long brandId, Long categoryId, int pageNum, int pageSize);

    void add(PmsProduct pmsProduct);

    void update(PmsProduct pmsProduct);

    void delete(Long id);

    PmsProduct get(Long id);

}