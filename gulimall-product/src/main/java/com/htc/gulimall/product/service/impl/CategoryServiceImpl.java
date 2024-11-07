package com.htc.gulimall.product.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.htc.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.common.utils.Query;

import com.htc.gulimall.product.dao.CategoryDao;
import com.htc.gulimall.product.entity.CategoryEntity;
import com.htc.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("parent_cid","sort");
        List<CategoryEntity> entityList = baseMapper.selectList(wrapper);
        //2、组装成父子的树形结构
        return listToTree(entityList);
    }

    private List<CategoryEntity> listToTree(List<CategoryEntity> entityList) {
        // 根据所有父节点分组
        Map<Long, List<CategoryEntity>> map = entityList.stream().collect(Collectors.groupingBy(CategoryEntity::getParentCid));
        // 组装父子树形结构
        return entityList.stream().map(e -> {
            e.setChildren(map.getOrDefault(e.getCatId(), new ArrayList<>()));
            return e;
        }).filter(e -> ObjUtil.equal(e.getParentCid(), 0L)).collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> list) {
        // TODO 1.检查当前的菜单是否被其他地方引用
        // 逻辑删除
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        return baseMapper.getCategoryHierarchy(catelogId)
                .stream()
                .map(CategoryEntity::getCatId)
                .toArray(Long[]::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StrUtil.isNotEmpty(category.getName())) {
            // 同步更新其他关联表中的数据
            categoryBrandRelationService.updateCateGory(category.getCatId(), category.getName());
            // TODO 更新其他关联
        }
    }

}