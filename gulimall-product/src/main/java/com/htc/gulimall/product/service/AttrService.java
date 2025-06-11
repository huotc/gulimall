package com.htc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.product.entity.AttrEntity;
import com.htc.gulimall.product.vo.AttrGroupRelationVo;
import com.htc.gulimall.product.vo.AttrRespVo;
import com.htc.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    /**
     * 根据分组id查找关联的所有基本属性
     *
     * @param attrgroupId 属性组ID
     * @return {@link List }<{@link AttrEntity }>
     * @methodName getRelationAttr
     * @author huotengchao
     * @date 2024/11/07
     */
    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    /**
     * 获取当前分组没有关联的所有基本属性
     *
     * @param params      params
     * @param attrgroupId 属性组ID
     * @return {@link PageUtils }
     * @methodName getNoRelationAttr
     * @author huotengchao
     * @date 2024/11/08
     */
    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
    
    /**
     * 在指定的所有属性集合里面，挑出检索属性
     *
     * @param attrIds 属性ID
     * @return {@link List }<{@link Long }>
     * @methodName selectSearchAttrIds
     * @author huotengchao
     * @date 2025/05/12
     */
    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

