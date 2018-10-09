package cn.org.zhixiang.annotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/09
 * describe:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@RestController
public @interface MiNiCodeController {
    Class value() ;
}
