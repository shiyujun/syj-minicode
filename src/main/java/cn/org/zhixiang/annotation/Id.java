package cn.org.zhixiang.annotation;

import java.lang.annotation.*;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/09
 * describe:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Id {
}
