package com.htc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.product.entity.ProductAttrValueEntity;

import java.util.Map;

/**
 * spu属性值
 *
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

