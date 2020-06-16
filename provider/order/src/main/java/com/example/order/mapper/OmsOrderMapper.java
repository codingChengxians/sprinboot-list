package com.example.order.mapper;

import com.example.order.entity.OmsOrder;
import com.example.tkmybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表(OmsOrder)表数据库访问层
 *
 * @author luok
 * @since 2020-06-03 11:17:21
 */

@Mapper
public interface OmsOrderMapper extends MyMapper<OmsOrder>{

}