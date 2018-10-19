package cn.org.zhixiang.config;


import cn.org.zhixiang.entity.IdField;
import cn.org.zhixiang.extend.DefaultExtend;
import cn.org.zhixiang.extend.ExtendInterface;
import cn.org.zhixiang.utils.Const;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Description :
 *
 * @author syj
 * CreateTime    2018/10/14
 * Description   全局配置管理类
 */
@ComponentScan("cn.org.zhixiang")
@MapperScan(basePackages = "cn.org.zhixiang.mapper")
@Configuration
public class EnableSyjMiniCodeConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = Const.ID_FIELD_NAME)
    public IdField idField() {
        return new IdField();
    }

    @Bean(Const.INSERT_EXTEND)
    @ConditionalOnMissingBean(name = Const.INSERT_EXTEND)
    public ExtendInterface insertExtend() {
        return new DefaultExtend();
    }

    @Bean(Const.UPDATE_EXTEND)
    @ConditionalOnMissingBean(name = Const.UPDATE_EXTEND)
    public ExtendInterface updateExtend() {
        return new DefaultExtend();
    }

}
