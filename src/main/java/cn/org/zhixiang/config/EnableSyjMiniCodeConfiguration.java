package cn.org.zhixiang.config;


import cn.org.zhixiang.entity.IdField;
import cn.org.zhixiang.utils.Const;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@ComponentScan("cn.org.zhixiang")
@Configuration
public class EnableSyjMiniCodeConfiguration {

    @Bean
    @Conditional(PageHelperCondition.class)
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
        return pageHelper;
    }

    @Bean
    @ConditionalOnMissingBean(name = Const.ID_FIELD_NAME)
    public IdField idField() {
        return new IdField();
    }

}
