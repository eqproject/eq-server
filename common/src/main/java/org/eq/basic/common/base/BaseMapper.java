package org.eq.basic.common.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.RecoverableDataAccessException;

import java.util.ArrayList;
import java.util.List;

public interface BaseMapper<Record, Example> {
    long countByExample(Example example);

    int deleteByExample(Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    void insertReturnId(Record record);

    int insertSelective(Record record);

    List<Record> selectByExample(Example example);

    default List<Record> selectByExampleWithBLOBs(Example example) {

        return new ArrayList<>();
    }

    Record selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

    int updateByExample(@Param("record") Record record, @Param("example") Example example);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    default int updateByPrimaryKeyWithBLOBs(Record record) {

        return 0;
    }

    default int updateByExampleWithBLOBs(@Param("record") Record record, @Param("example") Example example) {

        return 0;
    }

    /**
     * 插入并返回主键ID的方法
     * @param record
     * @return
     */
    Long insertSelectiveAndReturnId(Record record);
}