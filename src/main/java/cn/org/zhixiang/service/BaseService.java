package cn.org.zhixiang.service;

import cn.org.zhixiang.entity.GridPageRequest;
import com.github.pagehelper.PageInfo;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述
 */
public interface BaseService{

    public Object selectOneById(String id);

    public PageInfo<Object> selectByPage(GridPageRequest gridPageRequest);
}
