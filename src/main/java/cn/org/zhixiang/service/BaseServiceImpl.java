package cn.org.zhixiang.service;


import cn.org.zhixiang.entity.GridPageRequest;
import cn.org.zhixiang.entity.Result;
import cn.org.zhixiang.extend.ExtendInterface;
import cn.org.zhixiang.mapper.BaseMapper;
import cn.org.zhixiang.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * describe:
 *
 * @author syj
 * CreateTime 2018/09/30
 * Description
 */

public class BaseServiceImpl implements BaseService {

    private String baseResult;
    private String tableName;
    private String idField;

    private static BaseMapper baseMapper = SpringContextUtil.getBean(BaseMapper.class);
    private static ExtendInterface insertExtend = SpringContextUtil.getBean(Const.INSERT_EXTEND);
    private static ExtendInterface updateExtend = SpringContextUtil.getBean(Const.INSERT_EXTEND);

    public BaseServiceImpl() {
    }
    public BaseServiceImpl(String tableName, String baseResult, String idField) {
        this.tableName = tableName;
        this.baseResult = baseResult;
        this.idField = idField;
    }


    @Override
    public Map<String, Object> selectOneById(String id) {
        Map<String, Object> resultMap = baseMapper.selectOneById(baseResult, tableName, idField, id);
        return resultMap;
    }

    @Override
    public Result selectByPage(GridPageRequest gridPageRequest) {
        PageHelper.startPage(gridPageRequest.getPageNum(), gridPageRequest.getPageSize());
        String sql=buildSelectSql(gridPageRequest);
        List<Map<String, Object>> resultList = baseMapper.selectByPage(baseResult, tableName, sql);
        //Long count=baseMapper.count(tableName,sql);
        PageInfo pageInfo = new PageInfo(resultList);
        return new Result(pageInfo);
    }

    @Override
    public List<Map<String, Object>> selectBySelective(GridPageRequest gridPageRequest) {
        String sql=buildSelectSql(gridPageRequest);
        List<Map<String, Object>> resultList = baseMapper.selectByPage(baseResult, tableName, sql);
        return resultList;
    }
    @Override
    public List<Map<String, Object>> selectBySelective(String sql) {

        List<Map<String, Object>> resultList = baseMapper.selectBySelective(sql);
        return resultList;
    }

    @Override
    public void deleteById(String id) {
        baseMapper.deleteById(tableName, idField, id);
    }

    @Override
    public void deleteByIds(List<String> idList) {
        String ids = String.join(",", idList);
        baseMapper.deleteByIds(tableName, idField, ids);
    }


    @Override
    public void insertSelective(Object object) {
        StringBuffer keyBuffer = new StringBuffer();
        StringBuffer valueBuffer = new StringBuffer();

        Map<String, Object> objectMap;
        if (object instanceof Map) {
            objectMap = (Map) object;
        } else {
            objectMap = BeanMapUtil.beanToMap(object);
        }
        Map<String, String> insertMap = insertExtend.exectue();
        objectMap.putAll(insertMap);
        Set<Map.Entry<String, Object>> entrySet = objectMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue() == null) {
                continue;
            }
            keyBuffer.append("`");
            keyBuffer.append(FieldUtil.toUnderLineString(entry.getKey()));
            keyBuffer.append("`,");
            valueBuffer.append("\"");
            valueBuffer.append(entry.getValue().toString());
            valueBuffer.append("\",");
        }

        String insertKey = FieldUtil.subLastChar(keyBuffer);
        String valueKey = FieldUtil.subLastChar(valueBuffer);
        baseMapper.insert(tableName, insertKey, valueKey);

    }

    @Override
    public void updateByIdSelective(Object object) {
        StringBuffer keyBuffer = new StringBuffer();
        Map<String, Object> objectMap;
        if (object instanceof Map) {
            objectMap = (Map) object;
        } else {
            objectMap = BeanMapUtil.beanToMap(object);
        }

        Map<String, String> updateMap = updateExtend.exectue();
        objectMap.putAll(updateMap);

        Set<Map.Entry<String, Object>> entrySet = objectMap.entrySet();
        String id = null;
        keyBuffer.append("set ");
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue() == null) {
                continue;
            }
            if (!Objects.equals(idField, entry.getKey())) {
                keyBuffer.append("`");
                keyBuffer.append(FieldUtil.toUnderLineString(entry.getKey()));
                keyBuffer.append("` =\"");
                keyBuffer.append(entry.getValue().toString());
                keyBuffer.append("\",");
            } else {
                id = entry.getValue().toString();
            }
        }

        String param = FieldUtil.subLastChar(keyBuffer);
        baseMapper.update(tableName, param, idField, id);

    }

    private String buildSelectSql(GridPageRequest gridPageRequest) {
        StringBuffer sql = new StringBuffer("where 1=1 ");
        String search = SelectPagePackUtil.packSerach(gridPageRequest.getSearchMap(), "=");
        String largeSearch = SelectPagePackUtil.packSerach(gridPageRequest.getLargeSearchMap(), ">=");
        String smallSearch = SelectPagePackUtil.packSerach(gridPageRequest.getSmallSearchMap(), "<=");
        String notEquleSearch = SelectPagePackUtil.packSerach(gridPageRequest.getNotEquleSearchMap(), "!=");
        String likeSearch = SelectPagePackUtil.packLikeSerach(gridPageRequest.getLikeSearchMap());
        String group = SelectPagePackUtil.packGroup(gridPageRequest.getGroupArray());
        String order = SelectPagePackUtil.packOrder(gridPageRequest.getOrderMap());
        if (search != null) {
            sql.append(search);
        }
        if (largeSearch != null) {
            sql.append(largeSearch);
        }
        if (smallSearch != null) {
            sql.append(smallSearch);
        }
        if (notEquleSearch != null) {
            sql.append(notEquleSearch);
        }
        if (likeSearch != null) {
            sql.append(likeSearch);
        }
        if (group != null) {
            sql.append(group);
        }
        if (order != null) {
            sql.append(order);
        }
        return sql.toString();
    }
}
