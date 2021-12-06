package com.xia.yuauth.controller.web.annotation;

import java.lang.annotation.*;

/**
 * description
 *
 * @author wanghaoxin
 * date     2021/12/6 21:53
 * @version 1.0
 */

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyPageParams {

}
