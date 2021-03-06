package org.eq.basic.gen.entity.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by JoinHan on 2017/7/13.
 */
@XmlRootElement(name = "categories")
public class Categories {

    private List<Category> categoryList;

    @XmlElement(name = "category")
    public List<Category> getCategoryList() {

        return this.categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {

        this.categoryList = categoryList;
    }
}
