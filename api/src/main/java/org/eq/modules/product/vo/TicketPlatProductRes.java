package org.eq.modules.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 区块链返回结果实体映射
 * @author  kaka
 */
@Data
public class TicketPlatProductRes  implements Serializable {

    private TicketData data;

    private Meta meta;



    @Data
    public static class TicketData{

        private List<TicketProductVO> voucherList;

        private Page page;

    }

    @Data
    public static class Meta{

        private int code;

        private String message;

    }

    @Data
    public static class Page{
        private int pageSize;
        private int pageStart;
        private int pageTotal;
    }
}
