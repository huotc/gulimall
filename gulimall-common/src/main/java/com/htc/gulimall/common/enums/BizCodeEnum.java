package com.htc.gulimall.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为 5 为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 * 10: 通用
 * 001：参数格式校验
 * 11: 商品
 * 12: 订单
 * 13: 购物车
 * 14: 物流
 * @author huotengchao
 * @version V1.0
 * @enumName BizCodeEnum
 * @description 系统异常枚举
 * @since 2024/3/11 17:47
 */
@Getter
@AllArgsConstructor
public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    PRODUCT_UP_EXCEPTION(10002, "上架商品失败"),
    PRODUCT_DOWN_EXCEPTION(10003, "下架商品失败"),
    PRODUCT_DELETE_EXCEPTION(10004, "删除商品失败"),
    PRODUCT_UPDATE_EXCEPTION(10005, "修改商品失败"),
    PRODUCT_STOCK_EXCEPTION(10006, "商品库存更新失败");

    private final Integer code;
    private final String msg;

}
