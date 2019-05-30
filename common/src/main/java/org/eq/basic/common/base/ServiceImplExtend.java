package org.eq.basic.common.base;

import org.eq.basic.common.util.ParseUtil;
import org.eq.basic.common.util.StringLowUtils;
import com.github.pagehelper.PageInfo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class ServiceImplExtend<Mapper, Record, Example> extends BaseServiceImpl<Mapper, Record, Example>
        implements ServiceExtend<Record, Example> {

    @Override
    public BaseTableData findDataTableByExampleForPage(Example example, Integer pageNum, Integer pageSize) {

        BaseTableData<Record> baseTableData = new BaseTableData();
        PageInfo pageInfo = this.findListByExampleForPage(example, pageNum / pageSize + 1, pageSize);
        baseTableData.setData(pageInfo.getList());
        baseTableData.setRecordsFiltered(pageInfo.getTotal());
        // 查询总条数
        baseTableData.setRecordsTotal(pageInfo.getTotal());
        return baseTableData;
    }

    @Override
    public int deleteVirtualByExample(Example example) {

        try {
            Method updateByExample = this.getMapper().getClass().getDeclaredMethod("updateByExample", Object.class);
            Record record = ( (Class<Record>) ( (ParameterizedType) this.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[1]).newInstance();
            Method setDelFlag = record.getClass().getDeclaredMethod("setDelFlag", String.class);
            setDelFlag.invoke(record, BaseEntity.DEL_FLAG_DELETE);
            Object result = updateByExample.invoke(this.getMapper(), record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int deleteVirtualByPrimaryKey(Long id) {

        try {
            Record record = ( (Class<Record>) ( (ParameterizedType) this.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[1]).newInstance();
            Method updateByPrimaryKey = this.getMapper().getClass().getDeclaredMethod("updateByPrimaryKeySelective",
                    Object.class);
            Method setId = record.getClass().getDeclaredMethod("setId", Long.class);
            Method setDelFlag = record.getClass().getDeclaredMethod("setDelFlag", String.class);
            setId.invoke(record, id);
            setDelFlag.invoke(record, BaseEntity.DEL_FLAG_DELETE);
            Object result = updateByPrimaryKey.invoke(this.getMapper(), record);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            logger.debug(e.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteVirtualByPrimaryKeys(String ids) {

        try {
            if (StringLowUtils.isBlank(ids)) {
                return 0;
            }
            String[] idArray = ids.split(",");
            int count = 0;
            for (String idStr : idArray) {
                if (StringLowUtils.isBlank(idStr)) {
                    continue;
                }
                Long id = Long.parseLong(idStr);
                Object result = this.deleteVirtualByPrimaryKey(id);
                count += Integer.parseInt(String.valueOf(result));
            }
            return count;
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public List<Record> findListByRecord(Record record) {

        // 仅提供最简单条件的查询 复杂情况需自己实现
        Example example = this.getExampleFromEntity(record, null);
        return this.findListByExample(example);
    }

    @Override
    public Record selectByRecord(Record record) {

        // 仅提供最简单条件的查询 复杂情况需自己实现
        Example example = this.getExampleFromEntity(record, null);
        List<Record> list = this.findListByExample(example);
        if (list != null && !( list.isEmpty())) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public PageInfo findListByRecordForPage(Record record, Map<String, Object> params) {

        Example example = this.getExampleFromEntity(record, params);
        // 从params 里获取分页信息
        Integer pageNum = ParseUtil.getInteger((String) params.get("start"));
        Integer pageSize = ParseUtil.getInteger((String) params.get("length"));
        return this.findListByExampleForPage(example, pageNum, pageSize);
    }

    @Override
    public BaseTableData findDataTableByRecordForPage(Record record, Map<String, Object> params) {

        Example example = this.getExampleFromEntity(record, params);
        // 从params 里获取分页信息
        Integer pageNum = (Integer) params.get("start");
        Integer pageSize = (Integer) params.get("pageSize");
        return this.findDataTableByExampleForPage(example, pageNum, pageSize);
    }

    @Override
    public int deleteVirtualByRecord(Record record) {

        Example example = this.getExampleFromEntity(record, null);
        return this.deleteVirtualByExample(example);
    }

    @Override
    public int countByRecord(Record record) {

        Example example = this.getExampleFromEntity(record, null);
        return this.countByExample(example);
    }

    @Override
    public int deleteByRecord(Record record) {

        Example example = this.getExampleFromEntity(record, null);
        return this.deleteByExample(example);
    }
}
