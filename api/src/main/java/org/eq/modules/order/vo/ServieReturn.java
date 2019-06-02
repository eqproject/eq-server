package org.eq.modules.order.vo;

import lombok.Data;

import java.util.List;

/**
 * 服务层结果返回实体
 * @param <T>
 */
@Data
public class ServieReturn<T> {

    /**
     * 返回实体
     */
    private  T data;

    /**
     * 错误描述
     */
    private String errMsg;


    /**
     * 实体集合
     */
    private List<T> lists;

}
