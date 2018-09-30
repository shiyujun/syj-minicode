package cn.org.zhixiang.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述 判断是否存在PageHelper
 */
public class PageHelperCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        try {
            return SpringContextUtil.getBean("pageHelper")==null;
        }catch (NullPointerException e){
            return false;
        }

    }
}
