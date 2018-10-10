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
 * describe:
 */
@RestController
@RequestMapping("/syj-api")
public class BaseController {

    @RequestMapping(value = "/{entityName}/{id}", method = RequestMethod.GET)
    public Result selectOneById(@PathVariable String entityName, @PathVariable String id) {
        return new Result(ServiceBeanFactory.getBean(entityName).selectOneById(id)) ;
    }

    @RequestMapping(value = "/{entityName}/page", method = RequestMethod.POST)
    public Result selectByPage(@PathVariable String entityName, @RequestBody GridPageRequest gridPageRequest) {
        return new Result(ServiceBeanFactory.getBean(entityName).selectByPage(gridPageRequest)) ;
    }

    @RequestMapping(value = "/{entityName}/insert", method = RequestMethod.POST)
    public Result insert(@PathVariable String entityName, @RequestBody Object object) {
        ServiceBeanFactory.getBean(entityName).insertSelective(object);
        return new Result() ;
    }

    @RequestMapping(value = "/{entityName}/update", method = RequestMethod.PUT)
    public Result update(@PathVariable String entityName, @RequestBody Object object) {
        ServiceBeanFactory.getBean(entityName).updateByIdSelective(object);
        return new Result() ;
    }


    @RequestMapping(value = "/{entityName}/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String entityName, @PathVariable String id) {
        ServiceBeanFactory.getBean(entityName).deleteById(id);
        return new Result();
    }

    @RequestMapping(value = "/{entityName}/deleteByIds", method = RequestMethod.DELETE)
    public Result deleteByIds(@PathVariable String entityName, @RequestBody List<String> ids) {
        ServiceBeanFactory.getBean(entityName).deleteByIds(ids);
        return new Result();
    }
}
