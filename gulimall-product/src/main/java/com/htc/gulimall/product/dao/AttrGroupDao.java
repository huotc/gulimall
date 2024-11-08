package com.htc.gulimall.product.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htc.gulimall.product.entity.AttrEntity;
import com.htc.gulimall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    IPage<AttrEntity> getNoRelationAttr(IPage<AttrEntity> page, @Param("attrgroupId") Long attrgroupId, @Param("key") String key, @Param("attrType") Integer attrType);

}
