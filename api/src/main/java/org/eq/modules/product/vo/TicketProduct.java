package org.eq.modules.product.vo;

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
    private String voucherId;

    /**
     * 券名称
     */
    private String voucherName;


    /**
     * 券icon
     */
    private String voucherIcon;

    /**
     * 券描述
     */
    private String description;

    /**
     * 券面值
     */
    private String faceValue;

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
    private VoucherIssuer voucherIssuer;

    /**
     * 承兑方
     */
    private List<VoucherAcceptance> voucherAcceptance;


    /**
     * 规格
     */
    private List<TicketSpe> voucherProperties;

    @Data
    public class VoucherIssuer{
        private  String address;
        private  String icon;
        private  String name;
    }


    @Data
    public class VoucherAcceptance{
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
