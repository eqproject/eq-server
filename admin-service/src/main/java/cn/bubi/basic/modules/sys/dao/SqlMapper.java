package cn.bubi.basic.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 直接执行sql语句
 *
 * @author JoinHan
 *
 */
@Mapper
public interface SqlMapper {

    List<Map> superManagerSelect(@Param("value") String sql);

    void superManagerInsert(@Param("value") String sql);

    void superManagerDelete(@Param("value") String sql);

    void createNewTableFromTable(@Param("newTableName") String newTableName,
                                 @Param("oldTableName") String oldTableName);

    void createPrimaryKey(@Param("newTableName") String newTableName);

    void createPrimaryKeyAdd(@Param("newTableName") String newTableName);
}