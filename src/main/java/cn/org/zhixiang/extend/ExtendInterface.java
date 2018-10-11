package cn.org.zhixiang.extend;

import java.util.Map;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/11
 * describe: 当你插入或更新数据时如果需要扩展信息时请实现此接口。并注入这个bean到Spring中
 */
public interface ExtendInterface {
    public Map<String,String> exectue();
}
