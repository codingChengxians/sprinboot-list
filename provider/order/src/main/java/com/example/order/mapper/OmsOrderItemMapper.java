package com.example.order.mapper;

import com.example.order.entity.OmsOrderItem;
import com.example.tkmybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单中所包含的商品(OmsOrderItem)表数据库访问层
 *
 * @author luok
 * @since 2020-06-03 11:17:48
 */

@Mapper
public interface OmsOrderItemMapper extends MyMapper<OmsOrderItem>{

}