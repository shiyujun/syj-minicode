# syj-minicode
***

# 项目介绍

> 所有项目中都会存在的增删改查和分页查询，你是否曾想过把它们抽象出来。此项目为一个Java后端通用代码的抽象，立志于为后端开发者减少80%的工作量，使大家把大部分的精力解放到业务代码。

#  为什么选择syj-minicode

1.  你是否还在使用代码生成插件生成一些重复的代码？
2.  你是否还在按照 controller-->service-->dao-->mapper.xml的流程写代码？
3.  你的代码里是否存在大量的重复代码？
4.  你是否渴望一行代码都不用写就能完成一个简单的模块？
# 如何不写代码就完成一个简单的模块


## 当一些单表的增删改查时你只需要告诉前端来调用这几个接口就行了
> 下方出现的entityName为当前查询的对象名称或者数据库表名称（注意命名规范要符合驼峰命名法）
> 删除接口为物理删除，逻辑删除请使用update接口
1. 根据主键查询对象
```java
@RequestMapping(value = "/syj-api/{entityName}/{id}", method = RequestMethod.GET)
```
2. 分页查询
```java
@RequestMapping(value = "/syj-api/{entityName}/page", method = RequestMethod.POST)
@RequestBody： GridPageRequest
```
这里的GridPageRequest为分页查询的条件，看一下它的组成元素
```java
    /**
     * 查询关键字Map
     */
    private Map<String, String> searchMap;
    /**
     * 模糊查询关键字Map
     */
    private Map<String, String> likeSearchMap;
    /**
     * 排序关键字Map
     */
    private Map<String, String> orderMap;
    /**
     * 分组关键字数组
     */
    private String[] groupArray;

    private int pageNum;
    private int pageSize;
```
前端查询时只需要按照查询条件组装GridPageRequest对象就可以了
3. 插入
```java
@RequestMapping(value = "/syj-api/{entityName}/insert", method = RequestMethod.POST)
@RequestBody： Object（待插入对象）
```
4. 根据主键进行更新
```java
@RequestMapping(value = "/syj-api/{entityName}/update", method = RequestMethod.PUT)
@RequestBody： Object（待更新对象）
```
5. 根据主键进行删除
```java
@RequestMapping(value = "/syj-api/{entityName}/{id}", method = RequestMethod.DELETE)
```
6. 批量删除
```java
@RequestMapping(value = "/syj-api/{entityName}/deleteByIds", method = RequestMethod.DELETE)
@RequestBody： List<String> ids（待删除主键列表）
```
## 扩展接口
1. 因为默认情况下单个对象查询、修改、删除所依据的主键的字段名使用的都是"id",但是很可能有的项目使用的是userId、orderId、roleId等主键，如果你的项目符合这个条件注入下方这个bean。
```java
           @Bean
           public IdField idField() {
               Map<String,String> map=new ConcurrentHashMap<>();
               map.put("user","userId");
               map.put("order","orderId");
               map.put("role","roleId");
               IdField idField=new IdField();
               idField.setMap(map);
               return idField;
           } 
```
2. 有的项目在进行插入和更新时会加入创建人和修改人等信息。如果有此需求请按照如下方式调用

## 代码层面如何用？
> 插入和更新操作只会操作object对象值不为空的字段，所以为了避免误操作建议对象属性类型使用包装类

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
>相信看了上方的介绍你已经迫不及待的想要使用了
1. 引入syj-minicode

<dependency>
    <groupId>cn.org.zhixiang</groupId>
    <artifactId>syj-minicode</artifactId>
    <version>1.0.0</version>
 </dependency>

2. 注册syj-minicode

    因为并不是所有的项目都会使用SpringBoot,所以在注册这一步我们分为两种情况

    1. SpringBoot或SpringCloud项目

        您需要在启动类上增加一个注解

        `@EnableSyjMiniCode`

    2. Spring

    您需要提供一个可以被Spring管理的配置类。比如说：
```java
@Import(EnableSyjMiniCodeConfiguration.class)
@Configuration
public class SyjMiniCodeConfig {
}

```
# 更多
1. 如果将syj-minicode用于项目的请在此链接留下足迹，便于宣传。[开源登记](https://github.com/2388386839/syj-minicode/issues/1)
2. 如果有需要新功能的同志可以在此链接留下足迹，帮助syj-minicode更加完善。[新需求](https://github.com/2388386839/syj-minicode/issues/2)
# 作者信息

1. [个人网站](http://zhixiang.org.cn)
2. [GitHub](https://github.com/2388386839)
3. [码云](https://gitee.com/zhixiang_blog)


# 版本信息
1. 1.0.0

    syj-minicode上线

