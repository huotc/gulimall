package com.htc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

