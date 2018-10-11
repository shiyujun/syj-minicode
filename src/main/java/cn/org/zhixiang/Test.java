package cn.org.zhixiang;

import cn.org.zhixiang.entity.Result;
import cn.org.zhixiang.utils.BeanMapUtil;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        Map<String,Object> map=new HashMap<>();
        map.put("returnCode",202);
        map.put("returnMsg","异常信息");
        Result result;
        try {
            result= (Result)BeanMapUtil.MapToModel(map,new Result());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("1");
    }
}
