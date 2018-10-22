package cn.org.zhixiang.controller;

import cn.org.zhixiang.entity.GridPageRequest;
import cn.org.zhixiang.entity.Result;
import cn.org.zhixiang.utils.ServiceBeanFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/09
 * describe: 公共controller
 */
@RestController
@RequestMapping("/syj-api")
public class BaseController {
    /**
     * 
     * @param entityName 实体名/表名
     * @param id 主键
     * @return Result
     */
    @RequestMapping(value = "/{entityName}/{id}", method = RequestMethod.GET)
    public Result selectOneById(@PathVariable String entityName, @PathVariable String id) {
        return new Result(ServiceBeanFactory.getBean(entityName).selectOneById(id)) ;
    }

    /**
     * 
     * @param entityName 实体名/表名
     * @param gridPageRequest 分页查询对象
     * @return Result
     */
    @RequestMapping(value = "/{entityName}/page", method = RequestMethod.POST)
    public Result selectByPage(@PathVariable String entityName, @RequestBody GridPageRequest gridPageRequest) {
        return ServiceBeanFactory.getBean(entityName).selectByPage(gridPageRequest);
    }

    /**
     * 
     * @param entityName 实体名/表名
     * @param object 待插入对象
     * @return Result
     */
    @RequestMapping(value = "/{entityName}/insert", method = RequestMethod.POST)
    public Result insert(@PathVariable String entityName, @RequestBody Object object) {
        ServiceBeanFactory.getBean(entityName).insertSelective(object);
        return new Result() ;
    }

    /**
     * 
     * @param entityName 实体名/表名
     * @param object 待更新对象
     * @return Result
     */
    @RequestMapping(value = "/{entityName}/update", method = RequestMethod.PUT)
    public Result update(@PathVariable String entityName, @RequestBody Object object) {
        ServiceBeanFactory.getBean(entityName).updateByIdSelective(object);
        return new Result() ;
    }

    /**
     * 
     * @param entityName 实体名/表名
     * @param id 主键
     * @return Result
     */
    @RequestMapping(value = "/{entityName}/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String entityName, @PathVariable String id) {
        ServiceBeanFactory.getBean(entityName).deleteById(id);
        return new Result();
    }

    /**
     * 
     * @param entityName 实体名/表名
     * @param ids 主键列表
     * @return Result
     */
    @RequestMapping(value = "/{entityName}/deleteByIds", method = RequestMethod.DELETE)
    public Result deleteByIds(@PathVariable String entityName, @RequestBody List<String> ids) {
        ServiceBeanFactory.getBean(entityName).deleteByIds(ids);
        return new Result();
    }
}
