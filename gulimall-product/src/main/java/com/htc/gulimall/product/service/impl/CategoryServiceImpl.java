package com.htc.gulimall.product.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.common.utils.Query;
import com.htc.gulimall.product.dao.CategoryDao;
import com.htc.gulimall.product.entity.CategoryEntity;
import com.htc.gulimall.product.service.CategoryBrandRelationService;
import com.htc.gulimall.product.service.CategoryService;
import com.htc.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


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
    
    
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("getLevel1Categorys.....");
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }
    
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {

        List<CategoryEntity> selectList = baseMapper.selectList(null);
        //1、查出所有1级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        //2、封装数据
        Map<String, List<Catelog2Vo>> parent_cid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的一级分类，查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());
            //2、封装上面面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    //1、找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
                    if (level3Catelog != null) {
                        List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
                            //2、封装成指定格式
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(collect);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }


            return catelog2Vos;
        }));

        return parent_cid;
    }
    
    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parent_cid) {
        List<CategoryEntity> collect = selectList.stream().filter(item -> Objects.equals(item.getParentCid(), parent_cid)).collect(Collectors.toList());
        //return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
        return collect;
    }


}