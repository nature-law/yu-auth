package com.xia.yuauth.controller.web.annotation;


import java.lang.annotation.*;

/**
 * description 用于处理页面结果的转换
 *
 * @author Administrator
 * @date 2021/12/7 12:39
 */
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ResultConvert {
}
