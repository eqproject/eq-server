package org.eq.modules.product.entitys;

import lombok.Data;

import java.util.List;

/**
 * 区块链商品信息
 * @author  kaka
 */
@Data
public class TicketProduct {
    /**
     * 合约地址
     */
    private String contractAddress;

    /**
     * 券ID
     */
    private String ticketId;

    /**
     * 券名称
     */
    private String ticketName;


    /**
     * 券地址
     */
    private String ticketIcon;

    /**
     * 券描述
     */
    private String ticketDesc;

    /**
     * 券面值
     */
    private String ticketFaceValue;

    /**
     * 券开始时间
     */
    private String startTime;

    /**
     * 券结束时间
     */
    private String endTime;


    /**
     * 区块链分组id
     */
    private String trancheId;

    /**
     * 发行方
     */
    private TicketIssuer ticketIssuer;

    /**
     * 承兑方
     */
    private TicketAcceptance ticketAcceptance;


    /**
     * 规格
     */
    private List<TicketSpe> ticketSpe;

    @Data
    public class TicketIssuer{
        private  String address;
        private  String icon;
        private  String name;
    }


    @Data
    public class TicketAcceptance{
        private  String address;
        private  String icon;
        private  String name;
    }

    @Data
    class TicketSpe{
        private String key;
        private String value;
    }

}
