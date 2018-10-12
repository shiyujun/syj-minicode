package cn.org.zhixiang;

import cn.org.zhixiang.utils.BeanMapUtil;


import java.util.*;

public class Test {
    public static void main(String[] args){
        Map<String,Object> map=new HashMap<>();
        map.put("t1",202);

        map.put("t4",22L);
        map.put("t5",true);
        map.put("t6",'f');
        map.put("t7","zzzz");
        map.put("t8",new Date());
        map.put("t10",2.2);
        map.put("t11",20);
        T1 t1 = (T1) BeanMapUtil.mapToBean(map,T1.class);
        List<Map<String, Object>> maps =new ArrayList<>();

        maps.add(map);
        maps.add(map);
        maps.add(map);
        maps.add(map);
        maps.add(map);


        System.out.println("1");
    }

}
