package cn.org.zhixiang.utils;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述
 */
public class FieldUtil {
    /**
     * 驼峰命名转换为下划线模式
     * @param field 字段名称
     * @param ignore 小于等于0时转换
     * @return
     */
    public static String toUnderLineString(String field,Integer ignore){
        StringBuffer fieldBuffer=new StringBuffer();
        char[] fieldChars=field.toCharArray();
        for ( char c:fieldChars) {
            if(Character.isUpperCase(c)){
                if(ignore<=0){
                    fieldBuffer.append('_');
                }
                ignore--;
                fieldBuffer.append(Character.toLowerCase(c));
            }else {
                fieldBuffer.append(c);
            }
        }
        return fieldBuffer.toString();
    }
}
