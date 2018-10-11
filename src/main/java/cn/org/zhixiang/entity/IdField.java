package cn.org.zhixiang.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/10
 * describe: 当你的数据表主键名称不为id而为userId、orderId时，请使用对象名or表名作为key，主键字段名称作为value放到map中并注入这个bean到Spring中
 */
public class IdField {
    private Map<String,String> map=new ConcurrentHashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
