package org.eq.basic.gen.entity.page;

/**
 * @Author: JoinHan
 * @Date: Created in 12:11 2018/3/7
 * @Modified By：
 */
public class HtmlElement {

    private Integer elementType;// 元素类型 1：查询项 2：按钮 3：dataTabel列

    private String name;// 字段名

    private String sqlName;// 数据库字段名

    // ----------------------------- 查询项 元素属性 --------------------------
    private String selectName;// 查询名称

    private String selectType;// 查询方式

    private String showType;// 显示方式

    private String dictionaryDate;// 如果是数据字典 数据字典类型

    private Integer order;// 排序

    // ----------------------------- 按钮项 元素属性 --------------------------
    private String buttonName;// 按钮名称

    private String buttonType;// 按钮类型

    private String buttonPermission;// 按钮权限

    // ----------------------------- 按钮项 元素属性 --------------------------
    private String dataName;// 列名称

    public Integer getElementType() {

        return this.elementType;
    }

    public void setElementType(Integer elementType) {

        this.elementType = elementType;
    }

    public String getSelectName() {

        return this.selectName;
    }

    public void setSelectName(String selectName) {

        this.selectName = selectName;
    }

    public String getSelectType() {

        return this.selectType;
    }

    public void setSelectType(String selectType) {

        this.selectType = selectType;
    }

    public String getShowType() {

        return this.showType;
    }

    public void setShowType(String showType) {

        this.showType = showType;
    }

    public String getDictionaryDate() {

        return this.dictionaryDate;
    }

    public void setDictionaryDate(String dictionaryDate) {

        this.dictionaryDate = dictionaryDate;
    }

    public Integer getOrder() {

        return this.order;
    }

    public void setOrder(Integer order) {

        this.order = order;
    }

    public String getButtonName() {

        return this.buttonName;
    }

    public void setButtonName(String buttonName) {

        this.buttonName = buttonName;
    }

    public String getButtonType() {

        return this.buttonType;
    }

    public void setButtonType(String buttonType) {

        this.buttonType = buttonType;
    }

    public String getButtonPermission() {

        return this.buttonPermission;
    }

    public void setButtonPermission(String buttonPermission) {

        this.buttonPermission = buttonPermission;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDataName() {

        return this.dataName;
    }

    public void setDataName(String dataName) {

        this.dataName = dataName;
    }

    public String getSqlName() {

        return this.sqlName;
    }

    public void setSqlName(String sqlName) {

        this.sqlName = sqlName;
    }
}
