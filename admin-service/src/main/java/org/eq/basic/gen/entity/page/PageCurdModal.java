package org.eq.basic.gen.entity.page;

import java.util.List;

/**
 * @Author: JoinHan
 * @Date: Created in 11:52 2018/3/7
 * @Modified By：
 */
public class PageCurdModal extends PageModal {

    // ----------------------------- 查询项 设置 --------------------------
    List<HtmlElement> selectSectionList;

    List<HtmlElement> buttonSectionList;

    List<HtmlElement> dataTableSectionList;

    public List<HtmlElement> getSelectSectionList() {

        return this.selectSectionList;
    }

    public void setSelectSectionList(List<HtmlElement> selectSectionList) {

        this.selectSectionList = selectSectionList;
    }

    public List<HtmlElement> getButtonSectionList() {

        return this.buttonSectionList;
    }

    public void setButtonSectionList(List<HtmlElement> buttonSectionList) {

        this.buttonSectionList = buttonSectionList;
    }

    public List<HtmlElement> getDataTableSectionList() {

        return this.dataTableSectionList;
    }

    public void setDataTableSectionList(List<HtmlElement> dataTableSectionList) {

        this.dataTableSectionList = dataTableSectionList;
    }
}
