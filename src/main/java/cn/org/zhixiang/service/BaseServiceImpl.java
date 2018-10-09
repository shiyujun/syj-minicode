package cn.org.zhixiang.service;


import cn.org.zhixiang.annotation.Id;
import cn.org.zhixiang.config.SpringContextUtil;
import cn.org.zhixiang.entity.GridPageRequest;
import cn.org.zhixiang.mapper.BaseMapper;
import cn.org.zhixiang.utils.BeanMapUtil;
import cn.org.zhixiang.utils.FieldUtil;
import cn.org.zhixiang.utils.SelectPagePackUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述
 */

public class BaseServiceImpl implements BaseService {
    private Class clazz;
    private Field[] fields;
    private String baseResult;
    private String tableName;
    private String idField="id";
    Map<String,Object> entity=new HashMap<>();


    private static BaseMapper baseMapper= SpringContextUtil.getBean("baseMapper");

    public BaseServiceImpl(Class clazz){
        this.clazz=clazz;
        this.init();
    }
    private void init(){
        fields = clazz.getDeclaredFields();
        StringBuffer classNameBuffer=new StringBuffer(clazz.getName());
        String className=classNameBuffer.substring(classNameBuffer.lastIndexOf(".")+1,classNameBuffer.length());
        tableName=FieldUtil.toUnderLineString(className,1);
        StringBuffer baseResultBuffer=new StringBuffer();
        for (Field field : fields){
            String underLineString=FieldUtil.toUnderLineString(field.getName(),0);
            baseResultBuffer.append(underLineString+" as " + field.getName()+",");
            entity.put(field.getName(),underLineString);
            if(field.getAnnotation(Id.class)!=null){
                this.idField=underLineString;
            }
        }
        baseResult=baseResultBuffer.substring(0,baseResultBuffer.length()-1).toString();
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
    public long insertSelective(Object object) {
        StringBuffer keyBuffer=new StringBuffer();
        StringBuffer valueBuffer=new StringBuffer();
        for (Field field: fields){
            try {
                Field objectField = object.getClass().getDeclaredField(field.getName());
                objectField.setAccessible(true);
                Object objectValue= objectField.get(object);
                if(objectValue!=null){
                    keyBuffer.append("`");
                    keyBuffer.append(field.getName());
                    keyBuffer.append("`,");
                    valueBuffer.append("\"");
                    valueBuffer.append(objectValue.toString());
                    valueBuffer.append("\",");
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String insertKey=keyBuffer.substring(0,keyBuffer.length()-1).toString();
        String valueKey=valueBuffer.substring(0,valueBuffer.length()-1).toString();
        long id=baseMapper.insert(tableName,insertKey,valueKey);
        return id;
    }

    @Override
    public void updateByIdSelective(Object object) {
        StringBuffer keyBuffer=new StringBuffer();
        for (Field field: fields){
            try {
                Field objectField = object.getClass().getDeclaredField(field.getName());
                objectField.setAccessible(true);
                Object objectValue= objectField.get(object);
                if(objectValue!=null&& !Objects.equals("id",objectField.getName())){
                    keyBuffer.append("set `");
                    keyBuffer.append(field.getName());
                    keyBuffer.append("` =\"");
                    keyBuffer.append(objectValue);
                    keyBuffer.append("\", ");
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String param=keyBuffer.substring(0,keyBuffer.length()-1).toString();
        baseMapper.update(tableName,param,idField);

    }


}
