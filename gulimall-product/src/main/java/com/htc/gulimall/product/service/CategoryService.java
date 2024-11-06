package com.htc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> list);

    /**
     * 查找CatelogId的完整路径
     * [父/子/孙]
     *
     * @param catelogId 类别ID
     * @return {@link Long[] }
     * @methodName findCatelogPath
     * @author huotengchao
     * @date 2024/11/06
     */
    Long[] findCatelogPath(Long catelogId);
}

