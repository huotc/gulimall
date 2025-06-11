package com.htc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.product.entity.SkuInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
    
    /**
     * 通过spu id获取skus
     *
     * @param spuId SPI ID
     * @return {@link List }<{@link SkuInfoEntity }>
     * @methodName getSkusBySpuId
     * @author huotengchao
     * @date 2025/05/12
     */
    List<SkuInfoEntity> getSkusBySpuId(Long spuId);
}

