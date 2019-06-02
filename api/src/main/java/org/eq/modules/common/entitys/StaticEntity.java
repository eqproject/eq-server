package org.eq.modules.common.entitys;

/**
 * 此类主要定义基本数据
 * @author  kaka
 * @date  2019-05-28
 */
public class StaticEntity {

    /**
     * 每页最大条数
     */
    public static final int MAX_PAGE_SIZE=100;


    /**
     * 区块链获取请求令牌TOKEN
     */
    public static final String  TICKET_ACCESS_TOKEN_URL = "https://PCP/auth/accessToken";


    /**
     * 区块链请求平台商品地址
     */
    public static final String  TICKET_PRODUCT_URL = "https://{url}/ticket/v1/list";


    /**
     * 区块链请求用户商品地址
     */
    public static final String  TICKET_USER_PRODUCT_URL = "https://{url}/ticket/v1/account/balance";

    /**
     * 广告订单标题长度限制大小
     */
    public static final int ORDER_AD_TITLE_LENGTH=200;
}
