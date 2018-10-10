# syj-minicode
***

# 项目介绍

> 此项目为一个Java后端通用代码的抽象，立志于为后端开发者减少80%的工作量，使大家把大部分的精力解放到业务代码。

#  为什么选择syj-minicode

1.  你是否还在使用代码生成插件生成一些重复的代码？
2.  你是否还在按照 controller-->service-->dao-->mapper.xml的流程写下了一个一个表的增删改查代码？
3.  你的代码里是否存在大量的除了操作的表不一样而其他的逻辑完全一样的重复代码？
4.  你是否渴望一行代码都不用写就能完成一个简单的模块？
# 如何不写代码就完成一个简单的模块

## 代码层面如何用？
private BaseService getBaseService(){
        return ServiceBeanFactory.getBean("SUser");
    }
     Class<SUser> sUser=  BeanMapUtil.mapToBean(ServiceBeanFactory.getBean("SUser").selectOneById("115"),SUser.class) ;
            Class<Role> role=  BeanMapUtil.mapToBean(ServiceBeanFactory.getBean("SRole").selectOneById("115"),Role.class) ;
            GridPageRequest gridPageRequest=new GridPageRequest();
            gridPageRequest.setPageNum(0);
            gridPageRequest.setPageSize(10);
            PageInfo<Object> roleList= ServiceBeanFactory.getBean("SRole").selectByPage(gridPageRequest);
            PageInfo<Object> userList= ServiceBeanFactory.getBean("SUser").selectByPage(gridPageRequest);
            ServiceBeanFactory.getBean("SUser").deleteById("116");
            ServiceBeanFactory.getBean("SRole").deleteById("3");
            List<String> list=new ArrayList<>();
            list.add("117");
            ServiceBeanFactory.getBean("SUser").deleteByIds(list);
            ServiceBeanFactory.getBean("SRole").deleteByIds(list);
    
            SUser sUser1=new SUser();
            sUser1.setId(108L);
            ServiceBeanFactory.getBean("SUser").insertSelective(sUser1);
            Role role1=new Role();
            role1.setId(108L);
            ServiceBeanFactory.getBean("SRole").insertSelective(role1);
            sUser1.setUserName("1");
            role1.setRoleName("1");
            ServiceBeanFactory.getBean("SRole").updateByIdSelective(role1);
# Quick Start
1. 引入syj-minicode

<dependency>
    <groupId>cn.org.zhixiang</groupId>
    <artifactId>syj-minicode</artifactId>
    <version>1.0.0</version>
 </dependency>

2. 注册syj-ratelimit

    因为并不是所有的项目都会使用SpringBoot,所以在注册这一步我们分为两种情况

    1. SpringBoot或SpringCloud项目

        您需要在启动类上增加一个注解

        `@EnableSyjRateLimit`

    2. Spring

    您需要提供一个可以被Spring管理的配置类。比如说：
```java
@Import(EnableSyjMiniCodeConfiguration.class)
@Configuration
public class SyjRateLimitConfig {
}

```


3. 配置您的redis连接

    您需要配置您的redis连接为syj-ratelimit，同2的情况我们把项目分为两种情况（注意下方的配置需要根据实际情况调整）

1.SpringBoot或SpringCloud项目

spring:
  redis:
  
    #cluster: 集群模式启用
     # nodes: 10.0.20.135:7010,10.0.20.135:7011,10.0.20.135:7012
    host: 
    port: 
    password:
    pool:
      max-active: 8
      max-wait: 1
      max-idle: 8
      min-idle: 0
    timeout: 2000

2. Spring应用

您只需要注册一个RedisConnectionFactory子类的bean。比如说

<beans>
    <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig" >
        <property name="maxIdle" value="${redis.maxIdle}" />
        <property name="maxWaitMillis" value="${redis.maxWait}" />
        <property name="testOnBorrow" value="${redis.testOnBorrow}" />
    </bean>
    <bean id="connectionFactory"  class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory" >
        <property name="poolConfig" ref="poolConfig" />
        <property name="port" value="${redis.port}" />
        <property name="hostName" value="${redis.host}" />
        <property name="timeout" value="${redis.timeout}" ></property>
        <property name="database" value="1"></property>
    </bean>
</beans>

4. 使用syj-ratelimit

    其实看到这一步的时候您已经可以使用syj-ratelimit来进行限流了哦。

syj-ratelimit为您提供了两个注解来进行限流，它们是@ClassRateLimit和@MethodRateLimit。顾名思义，它们一个是用在类上的一个是用在方法上的。他们的功能是一样的，之所以分出来两个注解的原因就是为了解决当一个类的不同接口需要进行不同的限流方案问题

因为两个注解的内容一样，所以我们先以@ClassRateLimit为例看一下其中的几个属性

public @interface ClassRateLimit {
    /**
     * 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public CheckTypeEnum checkType() default CheckTypeEnum.ALL;
    /**
     * 限流次数。默认值10
     */
    public long limit() default 10;
    /**
     * 限流时间间隔,以秒为单位。默认值60
     */
    public long refreshInterval() default 60;

}

来几个使用的例子吧

    限流总资源数。（例如，需要每个方法每30秒只允许调用10次）

@ClassRateLimit(limit = 10,refreshInterval=30)
@RestController
@RequestMapping("/testClass")
public class TestClassRateLimitController {

    @PostMapping("/havaParam")
    public void havaParam(@RequestBody Map<String,String> map){
        System.out.println("业务逻辑。。。。");
    }

    @GetMapping("/noParam")
    public void noParam(){
        System.out.println("业务逻辑。。。。");
    }

}

    根据IP限流总资源数

@ClassRateLimit(limit = 10,refreshInterval=30,checkType = CheckTypeEnum.IP)//每个IP每30秒可以访问10次

    根据自定义信息限流总资源数（自定义时推荐在controller中查出能标识用户唯一性的值放入request中，然后把限流注解添加到service中进行限流）

@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @Autowired
    private TestService testCstom;

    @PostMapping("/custom")
    public void custom(HttpServletRequest httpServletRequest){
        //根据一系列操作查出来了用户id
        httpServletRequest.setAttribute(Const.CUSTOM,"用户id");//限流时在httpServletRequest中根据Const.CUSTOM的值进行限流
        testCstom.testCstom();
    }
}

@ClassRateLimit(limit = 10,refreshInterval=30,checkType = CheckTypeEnum.CUSTOM)
public class TestService {
    public  void testCstom(){
        System.out.println("此方法每个key为Const.CUSTOM的用户每30秒可以进入10次");
    }
}

    限流某个方法的并发数

@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @PostMapping("/defult")
    public void defult(){
        System.out.println("没有拦截");
    }

    @PostMapping("/ip")
    public void ip(){
        System.out.println("没有拦截");
    }

    @MethodRateLimit(checkType = CheckTypeEnum.USER)
    @PostMapping("/user")
    public void user(){
        System.out.println("根据用户信息拦截");//用户信息取自request.getUserPrincipal()
    }

}

更多信息

    相信看完了上方的Quick Start您已经迫不及待的想要将syj-ratelimit应用于生产了。我在这里为您提供了两种限流算法。您可以根据自己系统的需求选择自己需要的算法

限流算法

    如果您对限流算法不太了解的话可以先参考一下这篇文章http://zhixiang.org.cn

    计数器法
    程序默认使用计数器算法进行限流，如果您要使用计数器法的话无需要额外的配置。
    令牌桶算法
    如果您想要使用令牌桶算法的话，那么有两个需要注意的地方。
        再配置文件中指定算法为令牌桶算法。（推荐您使用yml文件或者properties文件）
            yml

           syj-rateLimit:
             algorithm: token

            properties

               syj-rateLimit.algorithm= token

        您需要将目光放到@ClassRateLimit上的另外两个属性上

        /**
         * 向令牌桶中添加数据的时间间隔,以秒为单位。默认值10秒
         */
        public long tokenBucketTimeInterval() default 10;
        /**
         * 每次为令牌桶中添加的令牌数量。默认值5个
         */
        public long tokenBucketStepNum() default 5;

再次开发

    如果您想使用别的算法，您可以在这fork项目进行开发

为了遵守代码的开闭原则，您在添加新的限流算法时请参考包ratelimit、config和algorithm
作者信息

    个人网站
    GitHub
    码云

项目实现设计的技术

    如何使用Redis执行Lua脚本
    我是如何把自定义注解应用到生产的
    大型网站限流算法的实现和改造
    IDEA中使用lombok插件
    SpringBoot条件注解@Conditional
    策略模式
    如何将自己的jar包发布到mavan中央仓库

版本信息
1.0.0

    syj-ratelimit上线

1.1.0

        修复计算令牌数取整问题
        修复redis集群部署slot不一致问题

项目介绍

    此项目为一个无侵入的应用级网关限流框架,如果您正在寻找一个网关限流的框架，使用syj-ratelimit是最明智的选择

为什么选择syj-ratelimit

    无需任何复杂配置文件，一个注解玩转syj-ratelimit
    细粒度控制，您可以控制同一个类中的A方法每分钟限流100而B方法每分钟限流200
    高灵活性，可根据自定义信息（如用户id、用户ip、用户权限等）进行限流、可灵活选择限流算法
    高可用性，使用redis+lua脚本的原子性为分布式系统保驾护航
    高可扩展性，可灵活添加限流算法

Quick Start
1. 引入syj-ratelimit

<dependency>
    <groupId>cn.org.zhixiang</groupId>
    <artifactId>syj-ratelimit</artifactId>
    <version>1.1.0</version>
 </dependency>

2. 注册syj-ratelimit

    因为并不是所有的项目都会使用SpringBoot,所以在注册这一步我们分为两种情况

1.SpringBoot或SpringCloud项目

您需要在启动类上增加一个注解

@EnableSyjRateLimit

2.Spring

您需要提供一个可以被Spring管理的配置类。比如说：

@Import(EnableSyjRateLimitConfiguration.class)
@Configuration
public class SyjRateLimitConfig {
}

3. 配置您的redis连接

    您需要配置您的redis连接为syj-ratelimit，同2的情况我们把项目分为两种情况（注意下方的配置需要根据实际情况调整）

1.SpringBoot或SpringCloud项目

spring:
  redis:
  
    #cluster: 集群模式启用
     # nodes: 10.0.20.135:7010,10.0.20.135:7011,10.0.20.135:7012
    host: 
    port: 
    password:
    pool:
      max-active: 8
      max-wait: 1
      max-idle: 8
      min-idle: 0
    timeout: 2000

2. Spring应用

您只需要注册一个RedisConnectionFactory子类的bean。比如说

<beans>
    <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig" >
        <property name="maxIdle" value="${redis.maxIdle}" />
        <property name="maxWaitMillis" value="${redis.maxWait}" />
        <property name="testOnBorrow" value="${redis.testOnBorrow}" />
    </bean>
    <bean id="connectionFactory"  class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory" >
        <property name="poolConfig" ref="poolConfig" />
        <property name="port" value="${redis.port}" />
        <property name="hostName" value="${redis.host}" />
        <property name="timeout" value="${redis.timeout}" ></property>
        <property name="database" value="1"></property>
    </bean>
</beans>

4. 使用syj-ratelimit

    其实看到这一步的时候您已经可以使用syj-ratelimit来进行限流了哦。

syj-ratelimit为您提供了两个注解来进行限流，它们是@ClassRateLimit和@MethodRateLimit。顾名思义，它们一个是用在类上的一个是用在方法上的。他们的功能是一样的，之所以分出来两个注解的原因就是为了解决当一个类的不同接口需要进行不同的限流方案问题

因为两个注解的内容一样，所以我们先以@ClassRateLimit为例看一下其中的几个属性

public @interface ClassRateLimit {
    /**
     * 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public CheckTypeEnum checkType() default CheckTypeEnum.ALL;
    /**
     * 限流次数。默认值10
     */
    public long limit() default 10;
    /**
     * 限流时间间隔,以秒为单位。默认值60
     */
    public long refreshInterval() default 60;

}

来几个使用的例子吧

    限流总资源数。（例如，需要每个方法每30秒只允许调用10次）

@ClassRateLimit(limit = 10,refreshInterval=30)
@RestController
@RequestMapping("/testClass")
public class TestClassRateLimitController {

    @PostMapping("/havaParam")
    public void havaParam(@RequestBody Map<String,String> map){
        System.out.println("业务逻辑。。。。");
    }

    @GetMapping("/noParam")
    public void noParam(){
        System.out.println("业务逻辑。。。。");
    }

}

    根据IP限流总资源数

@ClassRateLimit(limit = 10,refreshInterval=30,checkType = CheckTypeEnum.IP)//每个IP每30秒可以访问10次

    根据自定义信息限流总资源数（自定义时推荐在controller中查出能标识用户唯一性的值放入request中，然后把限流注解添加到service中进行限流）

@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @Autowired
    private TestService testCstom;

    @PostMapping("/custom")
    public void custom(HttpServletRequest httpServletRequest){
        //根据一系列操作查出来了用户id
        httpServletRequest.setAttribute(Const.CUSTOM,"用户id");//限流时在httpServletRequest中根据Const.CUSTOM的值进行限流
        testCstom.testCstom();
    }
}

@ClassRateLimit(limit = 10,refreshInterval=30,checkType = CheckTypeEnum.CUSTOM)
public class TestService {
    public  void testCstom(){
        System.out.println("此方法每个key为Const.CUSTOM的用户每30秒可以进入10次");
    }
}

    限流某个方法的并发数

@RestController
@RequestMapping("/testAnnotation")
public class TestRateLimitController {

    @PostMapping("/defult")
    public void defult(){
        System.out.println("没有拦截");
    }

    @PostMapping("/ip")
    public void ip(){
        System.out.println("没有拦截");
    }

    @MethodRateLimit(checkType = CheckTypeEnum.USER)
    @PostMapping("/user")
    public void user(){
        System.out.println("根据用户信息拦截");//用户信息取自request.getUserPrincipal()
    }

}

更多信息

    相信看完了上方的Quick Start您已经迫不及待的想要将syj-ratelimit应用于生产了。我在这里为您提供了两种限流算法。您可以根据自己系统的需求选择自己需要的算法

限流算法

    如果您对限流算法不太了解的话可以先参考一下这篇文章http://zhixiang.org.cn

    计数器法
    程序默认使用计数器算法进行限流，如果您要使用计数器法的话无需要额外的配置。
    令牌桶算法
    如果您想要使用令牌桶算法的话，那么有两个需要注意的地方。
        再配置文件中指定算法为令牌桶算法。（推荐您使用yml文件或者properties文件）
            yml

           syj-rateLimit:
             algorithm: token

            properties

               syj-rateLimit.algorithm= token

        您需要将目光放到@ClassRateLimit上的另外两个属性上

        /**
         * 向令牌桶中添加数据的时间间隔,以秒为单位。默认值10秒
         */
        public long tokenBucketTimeInterval() default 10;
        /**
         * 每次为令牌桶中添加的令牌数量。默认值5个
         */
        public long tokenBucketStepNum() default 5;

再次开发

    如果您想使用别的算法，您可以在这fork项目进行开发

为了遵守代码的开闭原则，您在添加新的限流算法时请参考包ratelimit、config和algorithm
作者信息

    个人网站
    GitHub
    码云

项目实现设计的技术

    如何使用Redis执行Lua脚本
    我是如何把自定义注解应用到生产的
    大型网站限流算法的实现和改造
    IDEA中使用lombok插件
    SpringBoot条件注解@Conditional
    策略模式
    如何将自己的jar包发布到mavan中央仓库

版本信息
1.0.0

    syj-ratelimit上线

1.1.0

        修复计算令牌数取整问题
        修复redis集群部署slot不一致问题


