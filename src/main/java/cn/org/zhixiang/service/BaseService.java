package cn.org.zhixiang.service;

import cn.org.zhixiang.entity.GridPageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

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

    public void deleteById(String id);

    public void deleteByIds(List<String> ids);

    public long insertSelective(Object object);

    public void updateByIdSelective(Object object);
}
