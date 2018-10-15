package cn.org.zhixiang.config;

import cn.org.zhixiang.utils.SpringContextUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * describe:
 *
 * @author syj
 * CreateTime 2018/09/30
 * Description 判断是否存在PageHelper
 */
public class PageHelperCondition implements Condition {
    /**
     *
     * @param conditionContext conditionContext
     * @param annotatedTypeMetadata annotatedTypeMetadata
     * @return 是否存在bean：pageHelper
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        try {
            return SpringContextUtil.getBean("pageHelper")==null;
        }catch (NullPointerException e){
            return false;
        }

    }
}
