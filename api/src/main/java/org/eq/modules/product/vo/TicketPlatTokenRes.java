package org.eq.modules.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 区块链返回结果实体映射
 * @author  kaka
 */
@Data
public class TicketPlatTokenRes implements Serializable {

    private TokenData data;

    private Meta meta;



    @Data
    public static class TokenData{

        private String accessToken;

        private int expiresIn;

    }

    @Data
    public static class Meta{

        private Integer code;

        private String message;

    }


}
