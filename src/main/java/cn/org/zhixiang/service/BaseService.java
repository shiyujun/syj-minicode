package cn.org.zhixiang.service;

import cn.org.zhixiang.entity.GridPageRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * describe:
 *
 * @author syj
 * CreateTime 2018/09/30
 * Description
 */
public interface BaseService{

    public Map<String,Object> selectOneById(String id);

    public PageInfo<Object> selectByPage(GridPageRequest gridPageRequest);

    public List<Map<String, Object>> selectBySelective(GridPageRequest gridPageRequest);

    public List<Map<String, Object>> selectBySelective(String sql);

    public void deleteById(String id);

    public void deleteByIds(List<String> ids);

    public void insertSelective(Object object);

    public void updateByIdSelective(Object object);
}
