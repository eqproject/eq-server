package org.eq.basic.common.base;

import org.eq.basic.common.util.SpringContextUtil;
import org.eq.basic.common.util.StringLowUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * service支持类
 * Created by JoinHan on 2017/4/17.
 */
public abstract class BaseServiceImpl<Mapper, Record, Example> implements BaseService<Record, Example> {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 错误信息文本
     */
    protected String errorInfo = "";

    private Mapper mapper;

    
    public Mapper getMapper() {

        return this.mapper;
    }

    public void setMapper(Mapper mapper) {

        this.mapper = mapper;
    }
    
    /**
     * 获取service出错原因
     *
     * @return String
     */
    @Override
    public String getErrorInfo() {

        return this.errorInfo;
    }

    @Override
    public List<Record> findListByExample(Example example) {

        try {
            Method selectByExample = this.mapper.getClass().getDeclaredMethod("selectByExample", Object.class);
            Object result = selectByExample.invoke(this.mapper, example);
            return (List<Record>) result;
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Record> findListByExampleWithBLOBs(Example example) {

        try {
            Method selectByExample = this.mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", Object.class);
            Object result = selectByExample.invoke(this.mapper, example);
            return (List<Record>) result;
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public PageInfo findListByExampleForPage(Example example, Integer pageNum, Integer pageSize) {

        try {
            Method selectByExample = this.mapper.getClass().getDeclaredMethod("selectByExample", Object.class);
            PageHelper.startPage(pageNum, pageSize, true);
            Object result = selectByExample.invoke(this.mapper, example);
            PageInfo pageInfo = new PageInfo((List<Record>) result);
            return pageInfo;
        } catch(Exception e) {
            this.logger.error(e.getMessage());
        }
        return new PageInfo<>();
    }

    @Override
    public PageInfo findListByExampleWithBLOBsForPage(Example example, Integer pageNum, Integer pageSize) {

        try {
            Method selectByExample = this.mapper.getClass().getDeclaredMethod("selectByExampleWithBLOBs", Object.class);
            PageHelper.startPage(pageNum, pageSize, true);
            Object result = selectByExample.invoke(this.mapper, example);
            PageInfo pageInfo = new PageInfo((List<Record>) result);
            return pageInfo;
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
        }
        return new PageInfo<>();
    }

    @Override
    public Record selectByPrimaryKey(Long id) {

        try {
            Method selectByExample = this.mapper.getClass().getDeclaredMethod("selectByPrimaryKey", Long.class);
            Object result = selectByExample.invoke(this.mapper, id);
            return (Record) result;
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public int countByExample(Example example) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("countByExample", Object.class);
            Object result = countByExample.invoke(this.mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteByExample(Example example) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("deleteByExample", Object.class);
            Object result = countByExample.invoke(this.mapper, example);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", Long.class);
            Object result = countByExample.invoke(this.mapper, id);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {

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
                Method deleteByPrimaryKey = this.mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", Long.class);
                Object result = deleteByPrimaryKey.invoke(this.mapper, id);
                count += Integer.parseInt(String.valueOf(result));
            }
            return count;
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int insertRecord(Record record) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("insert", Object.class);
            Object result = countByExample.invoke(this.mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int insertSelective(Record record) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("insertSelective", Object.class);
            Object result = countByExample.invoke(this.mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int updateByExample(Record record, Example example) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("updateByExample", Object.class,
                    Object.class);
            Object result = countByExample.invoke(this.mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("updateByPrimaryKeySelective",
                    Object.class);
            Object result = countByExample.invoke(this.mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Record record) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("updateByPrimaryKeyWithBLOBs",
                    Object.class);
            Object result = countByExample.invoke(this.mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int updateByExampleSelective(Record record, Example example) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("updateByExampleSelective", Object.class,
                    Object.class);
            Object result = countByExample.invoke(this.mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int updateByExampleWithBLOBs(Record record, Example example) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("updateByExampleWithBLOBs", Object.class,
                    Object.class);
            Object result = countByExample.invoke(this.mapper, record, example);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Record record) {

        try {
            Method countByExample = this.mapper.getClass().getDeclaredMethod("updateByPrimaryKey", Object.class);
            Object result = countByExample.invoke(this.mapper, record);
            return Integer.parseInt(String.valueOf(result));
        } catch(Exception e) {
            this.logger.debug(e.getMessage());
            ;
        }
        return 0;
    }

    @Override
    public void initMapper() {

        this.mapper = SpringContextUtil.getBean(this.getMapperClass());
    }

    /**
     * 获取类泛型class
     *
     * @return
     */
    public Class<Mapper> getMapperClass() {

        return (Class<Mapper>) ( (ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
