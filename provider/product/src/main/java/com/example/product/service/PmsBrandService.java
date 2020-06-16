package com.example.product.service;

import com.example.product.entity.PmsBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 品牌表(PmsBrand)表服务接口
 *
 * @author luok
 * @since 2020-06-07 11:06:03
 */
public interface PmsBrandService {
    PageInfo<PmsBrand> list(String filter, int pageNum, int pageSize);

    void add(PmsBrand pmsBrand);

    void update(PmsBrand pmsBrand);

    void delete(Long id);

    PmsBrand get(Long id);

    List<PmsBrand> listAll();

    void add(String name, String logo, Integer showStatus, Integer sort, String brandStory);
}