package cn.org.zhixiang.utils;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.*;

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

    /**
     * 将map装换为javabean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static Object MapToModel(Map<String, Object> map, Object o) throws Exception {
        if (!map.isEmpty()) {
            for (String k : map.keySet()) {
                Object v = null;
                if (!k.isEmpty()) {
                    v = map.get(k);
                }
                Field[] fields = null;
                fields = o.getClass().getDeclaredFields();
                String clzName = o.getClass().getSimpleName();
                for (Field field : fields) {
                    int mod = field.getModifiers();
                    if (field.getName().toUpperCase().equals(k.toUpperCase())) {
                        field.setAccessible(true);
                        String type = field.getType().toString();
                        if (type.endsWith("String")) {
                            if (v != null) {
                                v = v.toString();
                            } else {
                                v = "";
                            }
                        }
                        if (type.endsWith("Date")) {
                            v = new Date(v.toString());
                        }
                        if (type.endsWith("Boolean")) {
                            v = Boolean.getBoolean(v.toString());
                        }
                        if (type.endsWith("int")) {
                            v = new Integer(v.toString());
                        }
                        if (type.endsWith("Long")) {
                            v = new Long(v.toString());
                        }
                        field.set(o, v);
                    }
                }
            }
        }
        return o;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
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

    /**
     * 将List<Map<String,Object>>转换为List<T>
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        List<T> list = new ArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                try {
                    bean = clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }


}
