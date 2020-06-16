package com.example.product.entity;

import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 品牌表(PmsBrand)实体类
 *
 * @author luok
 * @since 2020-06-07 11:06:03
 */

@Data
@Accessors(chain = true)
@Table(name="pms_brand")
public class PmsBrand implements Serializable {
    private static final long serialVersionUID = 816942720046962471L;
    
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    private Long id;
    
         
    @Column(name = "name")
    private String name;
    /**
     * 首字母
     */     
    @Column(name = "first_letter")
    private String firstLetter;
         
    @Column(name = "sort")
    private Integer sort;
    /**
     * 是否为品牌制造商：0->不是；1->是
     */     
    @Column(name = "factory_status")
    private Integer factoryStatus;
         
    @Column(name = "show_status")
    private Integer showStatus;
    /**
     * 产品数量
     */     
    @Column(name = "product_count")
    private Integer productCount;
    /**
     * 产品评论数量
     */     
    @Column(name = "product_comment_count")
    private Integer productCommentCount;
    /**
     * 品牌logo
     */     
    @Column(name = "logo")
    private String logo;
    /**
     * 专区大图
     */     
    @Column(name = "big_pic")
    private String bigPic;
    /**
     * 品牌故事
     */     
    @Column(name = "brand_story")
    private String brandStory;
}