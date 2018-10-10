package cn.org.zhixiang.utils;

import java.util.Map;
import java.util.Set;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述
 */
public class SelectPagePackUtil {

    public static String packSerach(Map<String, String> searchMap){
        if(searchMap==null){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer();
        Set<Map.Entry<String, String>> entrySet= searchMap.entrySet();
        for (Map.Entry<String, String> entry:entrySet){
            stringBuffer.append(" and ");
            stringBuffer.append(FieldUtil.toUnderLineString(entry.getKey()));
            stringBuffer.append(" = \"");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("\"");
        }
        return stringBuffer.toString();
    }
    public static String packLikeSerach(Map<String, String> likeSearchMap){
        if(likeSearchMap==null){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer();
        Set<Map.Entry<String, String>> entrySet= likeSearchMap.entrySet();
        for (Map.Entry<String, String> entry:entrySet){
            stringBuffer.append(" and ");
            stringBuffer.append(FieldUtil.toUnderLineString(entry.getKey()));
            stringBuffer.append(" like \"%");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("\"");
        }
        return stringBuffer.toString();
    }
    public static String packOrder(Map<String, String> orderMap){
        if(orderMap==null){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer();
        Set<Map.Entry<String, String>> entrySet= orderMap.entrySet();
        stringBuffer.append(" order by  ");
        for (Map.Entry<String, String> entry:entrySet){
            stringBuffer.append(FieldUtil.toUnderLineString(entry.getKey()));
            stringBuffer.append("\" ");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("\",");
        }
        return FieldUtil.subLastChar(stringBuffer);
    }
    public static String packGroup(String[] groupArray){
        if(groupArray==null){
            return null;
        }
        StringBuffer stringBuffer=new StringBuffer();

        stringBuffer.append(" group by  ");
        for ( String entry:groupArray){
            stringBuffer.append("\"");
            stringBuffer.append(FieldUtil.toUnderLineString(entry));
            stringBuffer.append("\",");
        }
        return FieldUtil.subLastChar(stringBuffer);
    }
}
