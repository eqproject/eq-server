package cn.bubi.basic.common.util.excel;

import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/29 0:14.
 */
public class ExcelHeader implements Comparable<ExcelHeader> {

    /**
     * excel的标题名称
     */
    private String title;

    /**
     * 每一个标题的顺序
     */
    private int order;

    /**
     * 字段的类型
     */
    private String type;

    /**
     * 时间格式
     */
    private String dateFormat;

    /**
     * 是否是导入字段
     */
    private boolean isImport;

    /**
     * 是否是导出字段
     */
    private boolean isExport;

    /**
     * 字典类型
     */
    private String dictionary;

    /*
     * 导入导出数据值转换sql sql是否预缓存
     */
    private List<String> sqlList;

    private List<Boolean> sqlCash;

    /**
     * 说对应方法名称
     */
    private String methodName;

    public String getTitle() {

        return this.title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public int getOrder() {

        return this.order;
    }

    public void setOrder(int order) {

        this.order = order;
    }

    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getDateFormat() {

        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {

        this.dateFormat = dateFormat;
    }

    public boolean isImport() {

        return this.isImport;
    }

    public void setImport(boolean anImport) {

        this.isImport = anImport;
    }

    public boolean isExport() {

        return this.isExport;
    }

    public void setExport(boolean export) {

        this.isExport = export;
    }

    public String getDictionary() {

        return this.dictionary;
    }

    public void setDictionary(String dictionary) {

        this.dictionary = dictionary;
    }

    public List<String> getSqlList() {

        return this.sqlList;
    }

    public void setSqlList(List<String> sqlList) {

        this.sqlList = sqlList;
    }

    public String getMethodName() {

        return this.methodName;
    }

    public void setMethodName(String methodName) {

        this.methodName = methodName;
    }

    public List<Boolean> getSqlCash() {

        return this.sqlCash;
    }

    public void setSqlCash(List<Boolean> sqlCash) {

        this.sqlCash = sqlCash;
    }

    @Override
    public int compareTo(ExcelHeader o) {

        return this.order > o.order ? 1 : ( this.order < o.order ? -1 : 0);
    }
}