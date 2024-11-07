package com.htc.gulimall.product.vo;

import lombok.Data;

/**
 * @author huotengchao
 * @version V1.0
 * @className AttrRespVo
 * @description
 * @since 2024/11/7 16:49
 */
@Data
public class AttrRespVo extends AttrVo {
    /**
     * 分类名称
     */
    private String catelogName;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 分类路径
     */
    private Long[] catelogPath;
}
