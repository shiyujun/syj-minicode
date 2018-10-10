package cn.org.zhixiang.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/10
 * describe:
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
