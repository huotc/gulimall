package com.htc.gulimall.product.dao;

import com.htc.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三级分类
 * 
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {


    /**
     * 获取类别层次结构
     * 使用递归查询
     *
     * @param catId cat ID
     * @return {@link List }<{@link CategoryEntity }>
     * @methodName getCategoryHierarchy
     * @author huotengchao
     * @date 2024/11/06
     */
    List<CategoryEntity> getCategoryHierarchy(@Param("catId") Long catId);
	
}
