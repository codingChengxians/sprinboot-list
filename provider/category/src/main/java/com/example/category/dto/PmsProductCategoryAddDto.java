package com.example.category.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PmsProductCategoryAddDto {

    /**
     * 上机分类的编号：0表示一级分类
     */
    @NotNull(message = "请选择上级分类！")
    private Long parentId;

    @NotBlank(message = "分类名称不能为空！")
    private String name;
    /**
     * 分类级别：0->1级；1->2级
     */
    private Integer level;
    /**
     * 是否显示在导航栏：0->不显示；1->显示
     */
    private Integer navStatus;
    /**
     * 显示状态：0->不显示；1->显示
     */
    private Integer showStatus;

    private Integer sort;
    /**
     * 图标
     */
    private String icon;

    private String keywords;
    /**
     * 描述
     */
    private String description;
}

