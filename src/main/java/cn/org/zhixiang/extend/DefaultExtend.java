package cn.org.zhixiang.extend;

import java.util.HashMap;
import java.util.Map;
/**
 * Description :
 *
 * @author  syj
 * CreateTime    2018/10/14
 * Description   扩展类默认实现
 */
public class DefaultExtend implements ExtendInterface{

    @Override
    public Map<String, String> exectue() {
        return new HashMap<>();
    }
}
