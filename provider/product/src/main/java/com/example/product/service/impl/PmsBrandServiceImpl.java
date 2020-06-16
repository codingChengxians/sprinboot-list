package com.example.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.product.service.PmsBrandService;
import com.example.product.entity.PmsBrand;
import com.example.product.mapper.PmsBrandMapper;
import com.example.tkmybatis.MapperQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 品牌表(PmsBrand)表服务实现类
 *
 * @author luok
 * @since 2020-06-07 11:06:04
 */
@Service("pmsBrandService")
public class PmsBrandServiceImpl implements PmsBrandService {

    @Resource
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public PageInfo<PmsBrand> list(String filter, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsBrand> brands = new MapperQuery<>(PmsBrand.class)
                .andLike("name", "%" + filter + "%", StrUtil.isNotBlank(filter))
                .orderBy("sort",false)
                .query(pmsBrandMapper);
        return PageInfo.of(brands);
    }

    @Override
    public void add(PmsBrand pmsBrand) {
        pmsBrandMapper.insert(pmsBrand);
    }

    @Override
    public void update(PmsBrand pmsBrand) {
        pmsBrandMapper.updateByPrimaryKey(pmsBrand);
    }

    @Override
    public void delete(Long id) {
        pmsBrandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsBrand get(Long id) {
        return pmsBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listAll() {
        List<PmsBrand> pmsBrands = pmsBrandMapper.selectAll();
        return pmsBrands;
    }

    @Override
    public void add(String name, String logo, Integer showStatus, Integer sort,String brandStory) {
        PmsBrand pmsBrand = new PmsBrand()
                .setName(name)
                .setLogo(logo)
                .setShowStatus(showStatus)
                .setBrandStory(brandStory)
                .setSort(sort);
        pmsBrandMapper.insert(pmsBrand);
    }
}