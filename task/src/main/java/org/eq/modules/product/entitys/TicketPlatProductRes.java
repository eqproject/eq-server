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

    private String errCode;

    private String msg;



    @lombok.Data
    public static class TicketData{

        private List<TicketProduct> ticketList;

        private Page page;

    }

    @lombok.Data
    public static class Page{
        private int count;
        private int curSize;
        private int endOfGroup;
        private int firstResultNumber;
        private boolean nextFlag;
        private boolean queryTotal;
        private int size;
        private int start;
        private int startOfGroup;
        private int total;
    }
}
