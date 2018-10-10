package cn.org.zhixiang.service;


import cn.org.zhixiang.config.SpringContextUtil;
import cn.org.zhixiang.entity.GridPageRequest;
import cn.org.zhixiang.mapper.BaseMapper;
import cn.org.zhixiang.utils.BeanMapUtil;
import cn.org.zhixiang.utils.Const;
import cn.org.zhixiang.utils.FieldUtil;
import cn.org.zhixiang.utils.SelectPagePackUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述
 */

public class BaseServiceImpl implements BaseService {

    private String baseResult;
    private String tableName;
    private String idField;

    private static BaseMapper baseMapper= SpringContextUtil.getBean(Const.BASE_MAPPER_NAME);



    public BaseServiceImpl(String tableName, String baseResult, String idField) {
        this.tableName=tableName;
        this.baseResult=baseResult;
        this.idField=idField;
    }


    @Override
    public Map<String,Object> selectOneById(String id) {
        Map<String,Object> resultMap=baseMapper.selectOneById(baseResult,tableName,idField,id);
        return resultMap;
    }

    @Override
    public PageInfo<Object> selectByPage(GridPageRequest gridPageRequest) {
        PageHelper.startPage(gridPageRequest.getPageNum(), gridPageRequest.getPageSize());
        StringBuffer sql=new StringBuffer("where 1=1 ");
        String search= SelectPagePackUtil.packSerach(gridPageRequest.getSearchMap());
        String likeSearch=SelectPagePackUtil.packLikeSerach(gridPageRequest.getLikeSearchMap());
        String group=SelectPagePackUtil.packGroup(gridPageRequest.getGroupArray());
        String order=SelectPagePackUtil.packOrder(gridPageRequest.getOrderMap());
        if(search!=null){
            sql.append(search);
        }
        if(likeSearch!=null){
            sql.append(likeSearch);
        }
        if(group!=null){
            sql.append(group);
        }
        if(order!=null){
            sql.append(order);
        }
        List<Map<String, Object>> resultList=baseMapper.selectByPage(baseResult,tableName,sql.toString());
        PageInfo pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public void deleteById(String id) {
        baseMapper.deleteById(tableName,idField,id);
    }

    @Override
    public void deleteByIds(List<String> idList) {
        String ids= String.join(",",idList);
        baseMapper.deleteByIds(tableName,idField,ids);
    }



    @Override
    public void insertSelective(Object object) {
        StringBuffer keyBuffer=new StringBuffer();
        StringBuffer valueBuffer=new StringBuffer();

        Map<String,Object> objectMap;
        if(object instanceof Map){
            objectMap=(Map)object;
        }else{
            objectMap=BeanMapUtil.beanToMap(object);
        }
        Set<Map.Entry<String,Object>> entrySet= objectMap.entrySet();
        for (Map.Entry<String,Object> entry:entrySet){
            if(entry.getValue()==null){
                continue;
            }
            keyBuffer.append("`");
            keyBuffer.append(FieldUtil.toUnderLineString(entry.getKey()));
            keyBuffer.append("`,");
            valueBuffer.append("\"");
            valueBuffer.append(entry.getValue().toString());
            valueBuffer.append("\",");
        }

        String insertKey=FieldUtil.subLastChar(keyBuffer);
        String valueKey=FieldUtil.subLastChar(valueBuffer);
        baseMapper.insert(tableName,insertKey,valueKey);

    }

    @Override
    public void updateByIdSelective(Object object) {
        StringBuffer keyBuffer=new StringBuffer();
        Map<String,Object> objectMap;
        if(object instanceof Map){
            objectMap=(Map)object;
        }else{
            objectMap=BeanMapUtil.beanToMap(object);
        }
        Set<Map.Entry<String,Object>> entrySet= objectMap.entrySet();
        String id=null;
        keyBuffer.append("set ");
        for (Map.Entry<String,Object> entry:entrySet){
            if(entry.getValue()==null){
                continue;
            }
            if(!Objects.equals(idField,entry.getKey())){
                keyBuffer.append("`");
                keyBuffer.append(FieldUtil.toUnderLineString(entry.getKey()));
                keyBuffer.append("` =\"");
                keyBuffer.append(entry.getValue().toString());
                keyBuffer.append("\",");
            }else{
                id=entry.getValue().toString();
            }
        }

        String param=FieldUtil.subLastChar(keyBuffer);
        baseMapper.update(tableName,param,idField,id);

    }


}
