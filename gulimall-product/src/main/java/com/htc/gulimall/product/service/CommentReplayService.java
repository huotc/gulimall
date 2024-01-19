package com.htc.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htc.gulimall.common.utils.PageUtils;
import com.htc.gulimall.product.entity.CommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author huotengchao
 * @email ishuotc@163.com
 * @date 2024-01-18 16:44:58
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

