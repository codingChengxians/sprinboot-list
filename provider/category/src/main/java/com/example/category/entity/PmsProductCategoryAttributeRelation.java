package com.example.category.entity;

import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类）(PmsProductCategoryAttributeRelation)实体类
 *
 * @author luok
 * @since 2020-06-01 17:40:41
 */

@Data
@Accessors(chain = true)
@Table(name="pms_product_category_attribute_relation")
public class PmsProductCategoryAttributeRelation implements Serializable {
    private static final long serialVersionUID = -56283083841696502L;
    
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long id;
    
         
    @Column(name = "product_category_id")
    private Long productCategoryId;
         
    @Column(name = "product_attribute_id")
    private Long productAttributeId;
}