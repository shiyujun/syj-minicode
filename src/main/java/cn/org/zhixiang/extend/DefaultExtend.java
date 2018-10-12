package cn.org.zhixiang.extend;

import java.util.HashMap;
import java.util.Map;

public class DefaultExtend implements ExtendInterface{

    @Override
    public Map<String, String> exectue() {
        return new HashMap<>();
    }
}
