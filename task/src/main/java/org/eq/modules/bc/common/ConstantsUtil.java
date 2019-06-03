package org.eq.modules.bc.common;

public class ConstantsUtil {
	
	public final static Integer BC_SUCCESS = 0;
	
	public final static String ZERO = "0";
	
	public final static String WX_RESULT_CODE = "SUCCESS";
	
	 /** 存储账户中心accessToken **/
	public static String REDIS_ACCOUNT_CENTER_ACCESSTOKEN_TAG = "bu-talent-api:accountCenter:accountToken:";
	
	/** 微信jsApiTicket标签 **/
	public static String REDIS_WX_TICKET_TAG = "bu-talent-api:wx:store-ticket";
	
	/** 微信基础accessToken标签 **/
	public static String REDIS_WX_ACCESSTOKEN_TAG = "bu-talent-api:wx:store-accessToken";
	
	/**账户池 集合标签**/
	public final static String INITIATOR_LIST_TAG = "bu-talent-api:tx:initiator";
	/**单个 标签**/
	public final static String INITIATOR_SINGLE_TAG = "bu-talent-api:tx:initiator-m:";
	
	/** 微信用户会话密钥缓存标签 **/
	public static String REDIS_WX_USER_SESSION_KEY_TAG = "bu-talent-api:wx:store-session-key:";
	
	/** 用户订单物流信息缓存标签 **/
	public static String REDIS_EXPRESS_INFO_TAG = "bu-talent-api:order:store-express-info:";
	
	/** 达人店分享专属小程序码 **/
	public static String REDIS_TALENT_CODE_TAG = "bu-talent-api:talent:store-program-code:";
	
	/** 订单提货地址信息 **/
	public static String REDIS_ORDER_DELIVERY_ADDRESS_TAG = "bu-talent-api:order:store-delivery_address:";
}
