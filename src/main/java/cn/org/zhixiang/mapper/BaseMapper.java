package cn.org.zhixiang.mapper;

import org.apache.ibatis.annotations.*;

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

    @Select(" select ${baseResult} from `${tableName}` where id = '#{id}'")
    public Map<String, Object> selectOneById(@Param("baseResult") String baseResult, @Param("tableName") String tableName, @Param("id") String id);

    @Select(" select ${baseResult} from `${tableName}` ${sql}")
    public List<Map<String, Object>> selectByPage(@Param("baseResult") String baseResult,
                                            @Param("tableName") String tableName,
                                            @Param("sql") String sql);
    @Delete("delete from `${tableName}` where id = '#{id}'")
    void deleteById( @Param("tableName")String tableName,  @Param("id")String id);

    @Delete("delete from `${tableName}` where id in (${ids})")
    void deleteByIds( @Param("tableName")String tableName,  @Param("ids")String ids);

    @Insert("insert into  `${tableName}` (${insertKey}) values (${valueKey}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long insert(@Param("tableName")String tableName,
                @Param("insertKey")String insertKey,
                @Param("valueKey")String valueKey);
    @Update("update `${tableName}` ${param} where id =#{id}")
    void update(@Param("tableName")String tableName,
                @Param("param")String param,
                @Param("id")String id);
}
