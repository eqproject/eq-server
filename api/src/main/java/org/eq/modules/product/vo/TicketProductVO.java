package org.eq.modules.product.vo;

import lombok.Data;
import org.eq.modules.common.entitys.EntityBase;

import java.util.List;

/**
 * 券链商品信息
 * @author  kaka
 * @date 2019-05-30
 */
@Data
public class TicketProductVO{


    /**
     * 合约地址
     */
    private  String  contractAddress;

    /**
     * 券Id
     */
    private  String voucherId;

    /**
     * 券名称
     */
    private  String voucherName;

    /**
     * 券图片地址
     */
    private  String voucherIcon;

    /**
     * 券描述
     */
    private  String description;


    /**
     * 发行商
     */
    private  VoucherIssuer voucherIssuer;

    /**
     * 承兑商
     */
    private  List<VoucherAcceptance> voucherAcceptance;


    /**
     * 面值
     */
    private  String faceValue;


    /**
     * 商品参数
     */
    private List<param> voucherProperties;

    /**
     * 余额
     */
    private String balance;

    private String startTime;

    private String endTime;

    /**
     * 区块链券有效期分组ID
     */
    private String trancheId;




    @Data
    class VoucherIssuer{
        /**
         * 发行方区块链地址
         */
        private  String  address;

        /**
         * 发行方图片地址
         */
        private String icon;

        /**
         * 发行方名称
         */
        private String name;

    }

    @Data
    class VoucherAcceptance{
        /**
         * 称对方区块链地址
         */
        private  String  address;

        /**
         * 称对方图片地址
         */
        private String icon;

        /**
         * 称对方名称
         */
        private String name;

    }

    @Data
    class param{
        /**
         * 参数Key
         */
        private String key;


        /**
         * 参数值
         */
        private String value;
    }


}
