package com.example.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.product.service.PmsProductService;
import com.example.product.entity.PmsProduct;
import com.example.product.mapper.PmsProductMapper;
import com.example.provider.feign.CategoryServiceFeignClient;
import com.example.tkmybatis.MapperQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Struct;
import java.util.List;




/**
 * 商品信息(PmsProduct)表服务实现类
 *
 * @author luok
 * @since 2020-06-02 15:11:02
 */
@Service("pmsProductService")
public class PmsProductServiceImpl implements PmsProductService {

    @Resource
    private CategoryServiceFeignClient categoryServiceFeignClient;
    @Resource
    private PmsProductMapper pmsProductMapper;

    @Override
    public PageInfo<PmsProduct> list(String filter, Long brandId, Long categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProduct> list = new MapperQuery<>(PmsProduct.class)
                .andEqualTo("deleteStatus", false)
                .andEqualTo("brandId", brandId, brandId != null)
                .andEqualTo("productCategoryId", categoryId, categoryId != null)
                .and()
                .orEqualTo("id", filter.trim(), StrUtil.isNotBlank(filter))
                .orLike("name", "%"+filter.trim()+"%", StrUtil.isNotBlank(filter))
                .orLike("keywords", "%"+filter.trim()+"%", StrUtil.isNotBlank(filter)   )
                .query(pmsProductMapper);
        return PageInfo.of(list);
    }

    @Override
    public void add(PmsProduct pmsProduct) {
        pmsProductMapper.insert(pmsProduct);
    }

    @Override
    public void update(PmsProduct pmsProduct) {
        pmsProductMapper.updateByPrimaryKey(pmsProduct);
    }

    @Override
    public void delete(Long id) {
        pmsProductMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProduct get(Long id) {
        PmsProduct pmsProduct = pmsProductMapper.selectByPrimaryKey(id);
        String categoryName = categoryServiceFeignClient.getCategoryName(pmsProduct.getId());
        System.out.println(categoryName);
        return  pmsProduct;
    }
}