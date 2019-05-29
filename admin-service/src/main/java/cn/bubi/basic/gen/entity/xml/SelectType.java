package cn.bubi.basic.gen.entity.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: JoinHan
 * @Date: Created in 17:44 2018/2/1
 * @Modified By：
 */
@XmlRootElement(name = "selectType")
public class SelectType {

    private List<Field> fieldList;

    @XmlElement(name = "field")
    public List<Field> getFieldList() {

        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {

        this.fieldList = fieldList;
    }
}
