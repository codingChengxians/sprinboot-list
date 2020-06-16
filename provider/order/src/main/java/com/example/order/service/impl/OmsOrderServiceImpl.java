package com.example.order.service.impl;

import com.example.order.service.OmsOrderService;
import com.example.order.entity.OmsOrder;
import com.example.order.mapper.OmsOrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;




/**
 * 订单表(OmsOrder)表服务实现类
 *
 * @author luok
 * @since 2020-06-03 11:17:21
 */
@Service("omsOrderService")
public class OmsOrderServiceImpl implements OmsOrderService {

    @Resource
    private OmsOrderMapper omsOrderMapper;

    @Override
    public PageInfo<OmsOrder> list(String filter, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OmsOrder> omsOrders = omsOrderMapper.selectAll();
        return PageInfo.of(omsOrders);
    }

    @Override
    public void add(OmsOrder omsOrder) {
        omsOrderMapper.insert(omsOrder);
    }

    @Override
    public void update(OmsOrder omsOrder) {
        omsOrderMapper.updateByPrimaryKey(omsOrder);
    }

    @Override
    public void delete(Long id) {
        omsOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OmsOrder get(Long id) {
        return omsOrderMapper.selectByPrimaryKey(id);
    }
}