package com.example.order.service;

import com.example.order.entity.OmsOrder;
import com.github.pagehelper.PageInfo;

/**
 * 订单表(OmsOrder)表服务接口
 *
 * @author luok
 * @since 2020-06-03 11:17:21
 */
public interface OmsOrderService {
    PageInfo<OmsOrder> list(String filter, int pageNum, int pageSize);

    void add(OmsOrder omsOrder);

    void update(OmsOrder omsOrder);

    void delete(Long id);

    OmsOrder get(Long id);

}