package com.htc.gulimall.product.vo;

import com.htc.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author huotengchao
 * @version V1.0
 * @className AttrGroupWithAttrsVo
 * @description
 * @since 2024/11/8 17:08
 */
@Data
public class AttrGroupWithAttrsVo {

    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
