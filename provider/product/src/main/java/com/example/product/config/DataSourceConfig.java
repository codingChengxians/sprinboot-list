package com.example.product.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.product.mapper.PmsProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class DataSourceConfig {
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @PostConstruct
    private  void init(){
        this.pmsProductMapper.selectByPrimaryKey(0);
    }
}
