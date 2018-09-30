package cn.org.zhixiang.annotation;


import cn.org.zhixiang.config.EnableSyjMiniCodeConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/09/04
 * Description   开启限流功能的标识注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableSyjMiniCodeConfiguration.class)
public @interface EnableSyjMiniCode {

}
