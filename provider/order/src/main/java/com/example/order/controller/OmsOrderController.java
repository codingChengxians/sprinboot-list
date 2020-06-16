package com.example.order.controller;



import com.example.common.PageDto;
import com.example.order.entity.OmsOrder;
import com.example.order.service.impl.OmsOrderServiceImpl;
import org.springframework.web.bind.annotation.*;

import com.example.order.dto.OmsOrderSearchDto;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单表(OmsOrder)表控制层
 *
 * @author luok
 * @since 2020-06-03 11:17:21
 */
@RestController
@RequestMapping("/api/omsOrder")
public class OmsOrderController {
    /**
     * 服务对象
     */
    @Autowired
    private OmsOrderServiceImpl omsOrderService;

     @GetMapping("/list")
    public PageInfo<OmsOrder> selectAll(OmsOrderSearchDto dto, PageDto pageDto) {
        return this.omsOrderService.list(dto.getFilter(), pageDto.getPageNum(), pageDto.getPageSize());
    }

   @GetMapping("/get")
    public OmsOrder get(long id) {
        return this.omsOrderService.get(id);
    }
    
    @PostMapping("/add")
    public void insert(@RequestBody OmsOrder omsOrder) {
        this.omsOrderService.add(omsOrder);
    }
    
    @PostMapping("/update")
    public void update(@RequestBody OmsOrder omsOrder) {
        this.omsOrderService.update(omsOrder);
    }
    
    @RequestMapping("/delete")
    public void delete(Long id) {
        this.omsOrderService.delete(id);
    }
    
}