package com.zz.zzbutterknifeannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: zhuozhang6
 * @date: 2021/9/22
 * @email: zhuozhang6@iflytek.com
 * @Description:
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface ZzBindView {
    int value();
}
