package cn.org.zhixiang.utils;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述 map和对象的互相转换
 */
public class BeanMapUtil {
    /**
     * 将对象装换为map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }



    public static Object mapToBean(Map<String, Object> map, Class clazz)  {
        Object o= null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (!map.isEmpty()) {
            for (String k : map.keySet()) {
                Object v = null;
                if (!k.isEmpty()) {
                    v = map.get(k);
                }
                Field[] fields = null;
                fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().toUpperCase().equals(k.toUpperCase())) {
                        field.setAccessible(true);
                        try {
                            field.set(o, v);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return o;
    }

    /**
     * 将List<T1>转换为List<Map<String, Object>>
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }





}
