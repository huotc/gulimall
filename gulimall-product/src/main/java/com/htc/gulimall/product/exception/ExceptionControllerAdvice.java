package com.htc.gulimall.product.exception;

import com.htc.gulimall.common.enums.BizCodeEnum;
import com.htc.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huotengchao
 * @version V1.0
 * @className ExceptionControllerAdvice
 * @description 统一异常处理
 * @since 2024/3/11 17:36
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handleValidException(MethodArgumentNotValidException e){
        log.error("数据校验异常{}，异常类型{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION).put("data", map);
    }


    @ExceptionHandler(value = {Exception.class})
    public R handleException(Exception e){
        log.error("系统异常", e);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION);
    }

    @ExceptionHandler(value = {Throwable.class})
    public R handleException(Throwable e){
        log.error("系统异常", e);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION);
    }
}
