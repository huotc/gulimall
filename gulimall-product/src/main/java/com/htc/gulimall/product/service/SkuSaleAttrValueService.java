package com.htc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.product.entity.SkuSaleAttrValueEntity;

import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:57
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

