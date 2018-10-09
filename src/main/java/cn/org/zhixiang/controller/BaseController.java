package cn.org.zhixiang.controller;

import cn.org.zhixiang.annotation.MiNiCodeController;
import cn.org.zhixiang.entity.GridPageRequest;
import cn.org.zhixiang.entity.Result;
import cn.org.zhixiang.service.BaseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/09
 * describe:
 */
@MiNiCodeController(BaseService.class)
public class BaseController {

    public BaseService baseServiceImpl;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result selectOneById(@PathVariable String id) {
        return new Result(baseServiceImpl.selectOneById(id)) ;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result selectByPage(@RequestBody GridPageRequest gridPageRequest) {
        return new Result(baseServiceImpl.selectByPage(gridPageRequest)) ;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result insert(@RequestBody Object object) {
        baseServiceImpl.updateByIdSelective(object);
        return new Result() ;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody Object object) {
        baseServiceImpl.updateByIdSelective(object);
        return new Result() ;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        baseServiceImpl.deleteById(id);
        return new Result();
    }

    @RequestMapping(value = "/deleteByIds", method = RequestMethod.DELETE)
    public Result deleteByIds(@RequestBody List<String> ids) {
        baseServiceImpl.deleteByIds(ids);
        return new Result();
    }
}
