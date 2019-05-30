package org.eq.modules.common.entitys;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果基本类
 * @author  kaka
 * @date  2019-05-27
 */
@Data
public class PageResultBase<T> {

    public PageResultBase() {
        this.recordsTotal = 0;
        this.recordsFiltered = 0;
        this.data = new ArrayList<>();
    }

    long recordsTotal;

    long recordsFiltered;

    List<T> data;



}
