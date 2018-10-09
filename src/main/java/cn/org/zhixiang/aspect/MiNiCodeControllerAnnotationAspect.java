package cn.org.zhixiang.aspect;

import cn.org.zhixiang.annotation.MiNiCodeController;
import cn.org.zhixiang.service.BaseService;
import cn.org.zhixiang.service.BaseServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description :
 *
 * @author syj
 * CreateTime    2018/09/05
 * Description   MethodRateLimit注解切面类
 */
@Aspect
public class MiNiCodeControllerAnnotationAspect {

    /**
     * @param miNiCodeController 注解
     */
    @Pointcut("@within(miNiCodeController)")
    public void annotationPointcut(MiNiCodeController miNiCodeController) {
    }


    /**
     * @param joinPoint          切点
     * @param miNiCodeController 注解
     */
    @Before("@within(miNiCodeController)")
    public void doBefore(JoinPoint joinPoint, MiNiCodeController miNiCodeController) throws NoSuchFieldException, IllegalAccessException {
        Field field = joinPoint.getTarget().getClass().getField("baseServiceImpl");
        if (field.get(joinPoint.getTarget()) == null) {
            Class entityClass=joinPoint.getTarget().getClass().getAnnotation(MiNiCodeController.class).value();
            BaseService baseService = new BaseServiceImpl(entityClass);
            try {
                field.set(joinPoint.getTarget(), baseService);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
