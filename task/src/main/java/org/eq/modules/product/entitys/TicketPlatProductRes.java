package org.eq.modules.product.entitys;

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



    @lombok.Data
    public static class TicketData{

        private List<TicketProduct> voucherList;

        private Page page;

    }

    @lombok.Data
    public static class Meta{

        private int code;

        private String message;

    }

    @lombok.Data
    public static class Page{
        private int pageSize;
        private int pageStart;
        private int pageTotal;
    }
}
