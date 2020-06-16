package com.example.product.controller;


import com.example.common.PageDto;
import com.example.product.dto.PmsBrandAddDto;
import com.example.product.entity.PmsBrand;
import com.example.product.service.impl.PmsBrandServiceImpl;
import org.springframework.web.bind.annotation.*;

import com.example.product.dto.PmsBrandSearchDto;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌表(PmsBrand)表控制层
 *
 * @author luok
 * @since 2020-06-07 11:06:04
 */
@RestController
@RequestMapping("/api/pmsBrand")
public class PmsBrandController {
    /**
     * 服务对象
     */
    @Autowired
    private PmsBrandServiceImpl pmsBrandService;

    @GetMapping("/list")
    public PageInfo<PmsBrand> list(PmsBrandSearchDto dto, PageDto pageDto) {
        return this.pmsBrandService.list(dto.getFilter(), pageDto.getPageNum(), pageDto.getPageSize());
    }

    @GetMapping("/all")
    public List<PmsBrand> selectAll() {
        return this.pmsBrandService.listAll();
    }

    @GetMapping("/get")
    public PmsBrand get(long id) {
        return this.pmsBrandService.get(id);
    }

    @PostMapping("/add")
    public void insert(@RequestBody PmsBrandAddDto dto) {
        this.pmsBrandService.add(dto.getName(),dto.getLogo(),dto.getShowStatus(),dto.getSort(),dto.getBrandStory());
    }

    @PostMapping("/update")
    public void update(@RequestBody PmsBrand pmsBrand) {
        this.pmsBrandService.update(pmsBrand);
    }

    @RequestMapping("/delete")
    public void delete(Long id) {
        this.pmsBrandService.delete(id);
    }

}