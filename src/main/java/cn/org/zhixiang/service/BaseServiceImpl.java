package cn.org.zhixiang.service;



import cn.org.zhixiang.config.SpringContextUtil;
import cn.org.zhixiang.mapper.BaseMapper;
import cn.org.zhixiang.utils.FieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

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
    Map<String,Object> entity=new HashMap<>();


    private static BaseMapper baseMapper= SpringContextUtil.getBean("baseMapper");

    public BaseServiceImpl(Class clazz){
        this.clazz=clazz;
        this.init();
    }

    public void init(){
        fields = clazz.getDeclaredFields();
        StringBuffer classNameBuffer=new StringBuffer(clazz.getName());
        String className=classNameBuffer.substring(classNameBuffer.lastIndexOf(".")+1,classNameBuffer.length());
        tableName=FieldUtil.toUnderLineString(className,1);
        StringBuffer baseResultBuffer=new StringBuffer();
        for (Field field : fields){
            String underLineString=FieldUtil.toUnderLineString(field.getName(),0);
            baseResultBuffer.append(underLineString+" as " + field.getName()+",");
            entity.put(field.getName(),underLineString);
        }
        baseResult=baseResultBuffer.substring(0,baseResultBuffer.length()-1).toString();
    }
    @Override
    public Object selectOneById(String id) {
        Map<String,Object> resultMap=baseMapper.selectOneById(baseResult,tableName,id);
        return resultMap;
    }



}
