package cn.org.zhixiang.utils;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述 字段处理工具类
 */
public class FieldUtil {
    /**
     * 驼峰命名转换为下划线模式
     * @param field 字段名称
     * @return
     */
    public static String toUnderLineString(String field){
        StringBuffer fieldBuffer=new StringBuffer();
        char[] fieldChars=field.toCharArray();
        for ( char c:fieldChars) {
            if(Character.isUpperCase(c)){
                fieldBuffer.append('_');
                fieldBuffer.append(Character.toLowerCase(c));
            }
        }
        return fieldBuffer.toString();
    }
    /**
     * 获取表名
     * @param field 字段名称
     * @return
     */
    public static String getTableName(String field){
        StringBuffer fieldBuffer=new StringBuffer();
        char[] fieldChars=field.toCharArray();
        for (int i=0,n=fieldChars.length;i<n;i++) {
            if(i>0&&Character.isUpperCase(fieldChars[i])){
                fieldBuffer.append('_');
            }
            fieldBuffer.append(Character.toLowerCase(fieldChars[i]));
        }
        return fieldBuffer.toString();
    }
    /**
     * 下划线转驼峰命名方式
     * @param field 字段名称
     * @return
     */
    public static String toHumpString(String field){
        StringBuffer fieldBuffer=new StringBuffer();
        char[] fieldChars=field.toCharArray();
        for (int i=0,n=fieldChars.length;i<n;i++) {
            if(fieldChars[i]=='_'){
                if(i+1<n&&fieldChars[i+1]!='_'){
                    fieldChars[i+1]=Character.toUpperCase(fieldChars[i+1]);
                }
            }else{
                fieldBuffer.append(fieldChars[i]);
            }
        }
        return fieldBuffer.toString();
    }
    /**
     * 去除字符串最后一位
     * @param field 字段名称
     * @return
     */
    public static String subLastChar(StringBuffer field){
       if(field==null){
           return null;
       }
       return field.substring(0,field.length()-1).toString();
    }
}
