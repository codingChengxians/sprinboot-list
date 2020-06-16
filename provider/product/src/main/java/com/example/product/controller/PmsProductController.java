package com.example.product.controller;



import com.example.common.PageDto;
import com.example.product.entity.PmsProduct;
import com.example.product.service.impl.PmsProductServiceImpl;
import org.springframework.web.bind.annotation.*;

import com.example.product.dto.PmsProductSearchDto;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 商品信息(PmsProduct)表控制层
 *
 * @author luok
 * @since 2020-06-02 15:11:02
 */
@RestController
@RequestMapping("/api/pmsProduct")
public class PmsProductController {
    /**
     * 服务对象
     */
    @Autowired
    private PmsProductServiceImpl pmsProductService;
//    @PostConstruct
//    private void init(){
//    }

     @GetMapping("/list")
    public PageInfo<PmsProduct> selectAll(PmsProductSearchDto dto, PageDto pageDto) {
        return this.pmsProductService.list(dto.getFilter(),dto.getBrandId(),dto.getCategoryId(), pageDto.getPageNum(), pageDto.getPageSize());
    }

   @GetMapping("/get")
    public PmsProduct get(long id) {
        return this.pmsProductService.get(id);
    }
    
    @PostMapping("/add")
    public void insert(@RequestBody PmsProduct pmsProduct) {
        this.pmsProductService.add(pmsProduct);
    }
    
    @PostMapping("/update")
    public void update(@RequestBody PmsProduct pmsProduct) {
        this.pmsProductService.update(pmsProduct);
    }
    
    @RequestMapping("/delete")
    public void delete(Long id) {
        this.pmsProductService.delete(id);
    }
    
}