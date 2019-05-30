package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.EntityBase;

import java.util.List;

/**
 * 商品信息表
 * @author  kaka
 * @date 2019-05-29
 */
@Data
public class PageProductVO extends EntityBase {

    /**
     * 页数
     */
    private int pageNum;


    /**
     * 总共多少个
     */
    private long totalNum;

    /**
     * 分页数据
     */
    private List<ProductVO> productDatas;



}
