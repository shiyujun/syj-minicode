package cn.org.zhixiang.utils;

import cn.org.zhixiang.entity.IdField;
import cn.org.zhixiang.mapper.BaseMapper;
import cn.org.zhixiang.service.BaseService;
import cn.org.zhixiang.service.BaseServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/10
 * describe: service工厂类
 */
public class ServiceBeanFactory {

    private static BaseMapper baseMapper= SpringContextUtil.getBean(BaseMapper.class);

    private static Map<String,BaseService> beanMap= new ConcurrentHashMap<>();

    public static BaseService getBean(String entityName){
        String tableName=FieldUtil.getTableName(entityName);
        if(!beanMap.containsKey(tableName)){
            String baseResult=buildBaseResult(tableName);
            String idField=getIdField(tableName);
            BaseService baseServiceImpl=new BaseServiceImpl(tableName,baseResult,idField);
            beanMap.put(tableName,baseServiceImpl);
        }
        return beanMap.get(tableName);
    }



    private static String buildBaseResult(String tableName) {
        List<String> columnList=baseMapper.selectColumnName(tableName);
        StringBuffer baseResultBuffer=new StringBuffer();
        for (String column : columnList){
            String humpString=FieldUtil.toHumpString(column);
            baseResultBuffer.append(column);
            baseResultBuffer.append(" as ");
            baseResultBuffer.append(humpString);
            baseResultBuffer.append(",");
        }
        return FieldUtil.subLastChar(baseResultBuffer);
    }
    private static String getIdField(String tableName) {
       IdField idField= SpringContextUtil.getBean(Const.ID_FIELD_NAME);
       String idName=(String)idField.getMap().get(tableName);
       return idName == null ? "id" : idName;
    }
}
