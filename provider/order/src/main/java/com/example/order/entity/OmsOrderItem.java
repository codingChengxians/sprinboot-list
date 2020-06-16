package com.example.order.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单中所包含的商品(OmsOrderItem)实体类
 *
 * @author luok
 * @since 2020-06-03 11:17:48
 */

@Data
@Accessors(chain = true)
@Table(name="oms_order_item")
public class OmsOrderItem implements Serializable {
    private static final long serialVersionUID = -12966038961962850L;
    
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long id;
    
    /**
     * 订单id
     */     
    @Column(name = "order_id")
    private Long orderId;
    /**
     * 订单编号
     */     
    @Column(name = "order_sn")
    private String orderSn;
         
    @Column(name = "product_id")
    private Long productId;
         
    @Column(name = "product_pic")
    private String productPic;
         
    @Column(name = "product_name")
    private String productName;
         
    @Column(name = "product_brand")
    private String productBrand;
         
    @Column(name = "product_sn")
    private String productSn;
    /**
     * 销售价格
     */     
    @Column(name = "product_price")
    private BigDecimal productPrice;
    /**
     * 购买数量
     */     
    @Column(name = "product_quantity")
    private Integer productQuantity;
    /**
     * 商品sku编号
     */     
    @Column(name = "product_sku_id")
    private Long productSkuId;
    /**
     * 商品sku条码
     */     
    @Column(name = "product_sku_code")
    private String productSkuCode;
    /**
     * 商品分类id
     */     
    @Column(name = "product_category_id")
    private Long productCategoryId;
    /**
     * 商品促销名称
     */     
    @Column(name = "promotion_name")
    private String promotionName;
    /**
     * 商品促销分解金额
     */     
    @Column(name = "promotion_amount")
    private BigDecimal promotionAmount;
    /**
     * 优惠券优惠分解金额
     */     
    @Column(name = "coupon_amount")
    private BigDecimal couponAmount;
    /**
     * 积分优惠分解金额
     */     
    @Column(name = "integration_amount")
    private BigDecimal integrationAmount;
    /**
     * 该商品经过优惠后的分解金额
     */     
    @Column(name = "real_amount")
    private BigDecimal realAmount;
         
    @Column(name = "gift_integration")
    private Integer giftIntegration;
         
    @Column(name = "gift_growth")
    private Integer giftGrowth;
    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */     
    @Column(name = "product_attr")
    private String productAttr;
}