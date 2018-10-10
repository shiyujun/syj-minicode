package cn.org.zhixiang.mapper;

import org.apache.ibatis.annotations.*;

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

    @Select(" select ${baseResult} from `${tableName}` where `${idField}` = #{id}")
    public Map<String, Object> selectOneById(@Param("baseResult") String baseResult,
                                             @Param("tableName") String tableName,
                                             @Param("idField") String idField,
                                             @Param("id") String id);

    @Select(" select column_name from information_schema.columns where table_name='${tableName}'")
    public List<String> selectColumnName(@Param("tableName")String tableName);

    @Select(" select ${baseResult} from `${tableName}` ${sql}")
    public List<Map<String, Object>> selectByPage(@Param("baseResult") String baseResult,
                                                  @Param("tableName") String tableName,
                                                  @Param("sql") String sql);
    @Delete("delete from `${tableName}` where `${idField}` = #{id}")
    void deleteById( @Param("tableName")String tableName,
                     @Param("idField") String idField,
                     @Param("id")String id);

    @Delete("delete from `${tableName}` where `${idField}` in (${ids})")
    void deleteByIds( @Param("tableName")String tableName,
                      @Param("idField") String idField,
                      @Param("ids")String ids);

    @Insert("insert into  `${tableName}` (${insertKey}) values (${valueKey}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long insert(@Param("tableName")String tableName,
                @Param("insertKey")String insertKey,
                @Param("valueKey")String valueKey);
    @Update("update `${tableName}` ${param} where `${idField}` =#{id}")
    void update(@Param("tableName")String tableName,
                @Param("param")String param,
                @Param("idField") String idField);
}
