package cn.org.zhixiang.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述
 */

public interface BaseMapper {

    @Select(" select ${baseResult} from `${tableName}` where id = #{id}")
    public Map<String, Object> selectOneById(@Param("baseResult")String baseResult, @Param("tableName")String tableName, @Param("id")String id);

}
