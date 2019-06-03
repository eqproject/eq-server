package org.eq.modules.bc.enums;

public enum ExceptionEnum {
	
	/** 
	 * 1----- 公用
	 * 2----- 用户
     * 3----- 商品
     * 4----- 订单
     * 5----- 活动
	 * 6----- 系统
	 * */
	
	SUCCESS("0", "成功"),
	PROCESS("1", "处理中"),
	
	PARAME_ERROR("100001","参数错误"),
	START_PASSWORD_ERROR("100002","密码不能为空或错误"),
	INITIATOR_ACCOUNT_NULL("100003","提交人账户不存在"),
	APP_ID_ERROR("100004","非法APPID"),
	GOOGLE_AUTH_CODE("100005","GOOGLE验证码错误或为空"),
	OLD_PWD_ERROR("100006","旧密码错误"),
	PWD_UPDATE_ERROR("100007","密码更新异常"),
	
	USER_INFO_NOT_EXIST("200000","用户信息不存在"),
	USER_TOKEN_ERR("200003", "无效的用户令牌"),
	USER_INFO_IS_FREEZE("200004","用户已被冻结"),
    WX_CODE_ERR("200005","无效的微信凭证"),
    USER_TALENT_ERROR("200006","用户已开通达人"),
    USER_IS_NOT_TALENT_ERROR("200007","该用户未开通达人店"),
    USER_WITHDRAW_AMOUNT_ERROR("200008","提现金额错误"),
    USER_BALANCE_ERROR("200009","用户余额不足"),
    USER_OPT_OFTEN_ERROR("200010","操作频繁，稍后在试"),
    USER_TALENT_EXIST("200011","达人店不存在"),
    USER_GOODS_QUANTITY_ERROR("200012","用户资产不足"),
    USER_TALENT_GOODS_EXIST("200013","达人店商品不存在"),
    USER_WITHDRAW_COUNT_ERROR("200014","提现次数已使用完"),
    USER_REFUND_COUNT_ERROR("200015","每日申请退款次数最多为三次"),
    USER_DELIVERY_ADDRESS_EXIST("200016","用户提货地址不存在"),
    USER_WITHDRAW_MAX_AMOUNT_ERROR("200017","每日最多可提现5000元"),
    USER_COUPON_OVERDUE_ERROR("200018","糟糕，优惠券过期了！"),
    
	GOODS_SKU_EXIST("300000","商品不存在"),
	GOODS_STOCK_ERROR("300001","商品存库不足"),
	TALENT_GOODS_INFO_ERROR("300002","达人店商品信息异常"),
	GOODS_COMMISSION_EXIST("300003","商品佣金不存在"),
	GOODS_PIC_EXIST("300004","商品图片信息不存在"),
	GOODS_CATEGORY_EXIST("300005","商品分类不存在"),
	GOODS_SUPPLIER_EXIST("300006","商品供货商信息不存在"),
	
	
	ORDER_QUANTITY_ERROR("400001","订单数量错误"),
	ORDER_TOTAL_MONEY_ERROR("400002","订单总价错误"),
	ORDER_QUANTITY_MAX_ERROR("400003","最大可购买9999数量"),
	ORDER_ID_NOT_EXIST("400004","订单不存在"),
	ORDER_STATUS_PAY_SUCCESS("400005","订单已经支付成功"),
	ORDER_STATUS_PAY_FAIL("400006","订单已经支付失败"),
	ORDER_PAY_FAIL("400007","支付失败"),
	ORDER_STATUS_ERROR("400008","订单状态错误,不能退款"),
	ORDER_REFUND_ERROR("400009","退款失败"),
	ORDER_DELIVERY_EXIST("400010","订单提货信息不存在"),
	ORDER_EXPRESS_NO_ERROR("400011","订单物流号异常"),
	ORDER_TIMEOUT_ERROR("400012","订单已超过7天不能退款"),
	ORDER_PLCAE_ERROR("400013","下单失败"),

	ACTIVITY_TEAM_NO_NOT_EXIST("500001","战队编号不存在"),
	AVAILABLE_VOTE_INSUFFICIENT("500002","可投票数不足"),
	QUESTIONNAIRE_RECORD_ALREADY_EXIST("500003","该用户已提交过调查问卷"),
	GROUP_ID_NO_EXIST("500004","拼团ID不存在"),
	ACTIVITY_DAILY_STATISTIC("500005","活动统计表为空"),
	LOTTERY_USER_INSUFFICIENT("500006","参与抽奖人数不足"),
	QUESTIONNAIRE_RECORD_NOT_EXIST("500007","该用户未提交过调查问卷"),
	LOTTERY_TODAY_ALREADY("500008","今日已进行过抽奖"),
	SHARE_USER_EQUALS_VOTE_USER("500009","不能自己邀请自己投票"),
	VOTE_COUNT_FORMAT_ERROR("500010","投票数格式不正确"),
	VOTE_COUNT_ZERO_ERROR("500011","投票数不能为0"),
	UPDATE_WINNING_LIST_READ_USER_MISMATCHING("500012","不能操作非自己的中奖纪录"),
	GROUP_REWARD_COUPON("500013","已领取过了"),
	ACTIVITY_TASK_NOT_EXIST("500014","无效的任务类型"),
	ACTIVITY_TASK_ALREADY_DONE("500015","该任务已完成"),
	VOTE_COUNT_BIG("500016","投票数量不能大于1000"),
	VOTE_CLOSE("500017","投票未开启"),

	
	SYS_ERR("600000", "系统内部错误"),
	GET_WX_ACCESSTOKEN_ERROR("600001","获取微信accessToken异常"),
	GET_WX_USER_INFO_ERROR("600002","获取微信用户信息异常"),
	ACCOUNT_CENTER_REGISTER_ERROR("600003","账户中心注册失败"),
	WX_AUTH_ERROR("600004","微信授权jssdk异常"),
	GET_ACCOUNT_CENTER_ACCESSTOKEN_ERROR("600005","获取账户中心accessToken异常"),
	ACCOUNT_CENTER_SIGN_ERROR("600006","账户中心签名异常"),
	GET_WX_BASE_USER_INFO_ERROR("600007","获取微信用户基本信息异常"),
	UNLAWFUL_OPERATION("600008","非法操作"),
	WX_SEND_SERVICE_NOTIFICATION("600009","发送微信模板消息失败"),
	IMG_DOWNLOAD_ERROR("600010","图片下载失败"),
	WX_MINI_PROGRAM_CODE_ERROR("600011","获取微信小程序码异常"),
	BC_TX_EXIST("600012","区块链交易不存在"),
	NETWORK_ERROR("600013","网络异常"),
    ;
	
	
    private final String code;
    private final String chineseMessage;

    private ExceptionEnum(String code, String chineseMessage) {
        this.code = code;
        this.chineseMessage = chineseMessage;
    }

    public String getCode() {
        return code;
    }


    public String getChineseMessage() {
        return chineseMessage;
    }
}