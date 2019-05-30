package org.eq.basic.gen.entity.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by JoinHan on 2017/7/13.
 */
@XmlRootElement(name = "category")
public class Category extends Field {

    private List<String> templateList;// 模板列表

    @XmlElement(name = "template")
    public List<String> getTemplateList() {

        return this.templateList;
    }

    public void setTemplateList(List<String> templateList) {

        this.templateList = templateList;
    }
}