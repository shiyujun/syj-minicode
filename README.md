# syj-minicode


# 项目介绍

> 此项目把Java后端最常用的代码全都抽象出来，一经使用即可减少开发者80%的工作量，使大家把所有的精力解放到业务代码中。

#  为什么选择syj-minicode

1. 你是否懒得写普通的增删改查方法?
2. 你是否不喜欢代码生成插件的重复代码？
3. 你是否渴望一个没有冗余代码的项目？
4. 你是否渴望一行代码都不用写就能完成一个简单的模块？
# 如何不写代码就完成一个简单的模块


### 当一些单表的增删改查时你只需要告诉前端来调用这几个接口就行了
> 下方出现的entityName为当前操作的对象名称或者数据库表名称（注意命名规范要符合驼峰命名法，例如：UserOrder或者user_order都可以）
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
       /**
       * 第几页
       */
       private Integer pageNum;
       /**
        * 查几条
        */
       private Integer pageSize;
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
### 扩展接口
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
    1. 插入扩展
    创建一个名为InsertExtend的bean并实现DefaultExtend接口
    ```java
    @Component
    public class InsertExtend implements ExtendInterface{
        @Override
        public Map<String, String> exectue() {
            Map<String, String> map=new HashMap<>();
            map.put("createTime", System.currentTimeMillis());
            map.put("createBy", "创建人id");
            map.put("createUserName", "创建人名称");
            return map;
        }
    }
    ```
   2. 更新扩展
     创建一个名为UpdateExtend的bean并实现DefaultExtend接口
    ```java
        @Component
        public class UpdateExtend implements ExtendInterface{
            @Override
            public Map<String, String> exectue() {
                Map<String, String> map=new HashMap<>();
                map.put("updateTime", System.currentTimeMillis());
                map.put("updateBy", "修改人id");
                map.put("updateUserName", "修改人名称");
                return map;
            }
        }
    ```
# 还有没有更灵活的使用方式？
> 上方使用方式其实是直接抽象到了controller层，解决一般的需要是没问题的，但是我们是有业务逻辑的，那么存在业务逻辑的情况下如何使用呢？
> 你可以在处理完业务逻辑后在service中调用

1. 初始化
    ```java
    private BaseService getUserBaseService(){
            return ServiceBeanFactory.getBean("User");
        }
    ```
2. 根据id查询
    ```java
    Map<String,Object> userMap=getUserBaseService().selectOneById("115");
    User user=(User) BeanMapUtil.mapToBean(map,User.class);
    ```
3. 根据条件查询列表（相信你已经知道了gridPageRequest对象如何组装）
    ```java
    List<Map<String, Object>> userMaps=getUserBaseService().selectBySelective(gridPageRequest);
    for (Map<String, Object> map:userMaps){
        User user=(User) BeanMapUtil.mapToBean(map,User.class);    
    }
    ```
4. 插入
    ```java
    getUserBaseService().insertSelective(user);
    ```
5. 更新
    ```java
    getUserBaseService().updateByIdSelective(user);
    ```
6. 删除
    ```java
    getUserBaseService().deleteById("115");
    ```
6. 批量删除
    ```java
    List<String> list=new ArrayList<>();
    list.add("115");
    list.add("116");
    list.add("117");
    getUserBaseService().deleteByIds(list);
    ```
# Quick Start
>相信看了上方的介绍你已经迫不及待的想要使用了，那么使用起来简单么？看一下你就知道。
1. 引入syj-minicode

    ```xml
    <dependency>
        <groupId>cn.org.zhixiang</groupId>
        <artifactId>syj-minicode</artifactId>
        <version>1.0.0</version>
     </dependency>
    ```
2. 注册syj-minicode

    因为并不是所有的项目都会使用SpringBoot,所以在注册这一步我们分为两种情况

    1. SpringBoot或SpringCloud项目
        你需要在启动类上增加一个注解
        ```java
        @EnableSyjMiniCode
        ```
    2. Spring

        你需要提供一个可以被Spring管理的配置类。比如说：
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

   > syj-minicode上线

