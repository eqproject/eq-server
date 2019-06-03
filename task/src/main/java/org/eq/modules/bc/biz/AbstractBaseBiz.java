package org.eq.modules.bc.biz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.bumo.mall.talent.common.ConstantsUtil;
import io.bumo.mall.talent.common.util.RedisUtil;
import io.bumo.mall.talent.common.util.StringUtil;
import io.bumo.mall.talent.common.util.Tools;
import io.bumo.mall.talent.common.util.UserTokenJWTUtil;
import io.bumo.mall.talent.common.validate.BaseInfoValidator;
import io.bumo.mall.talent.domain.GoodsCategory;
import io.bumo.mall.talent.domain.SupplierInfo;
import io.bumo.mall.talent.domain.Talent;
import io.bumo.mall.talent.domain.UserInfo;
import io.bumo.mall.talent.enums.ExceptionEnum;
import io.bumo.mall.talent.enums.UserStateEnum;
import io.bumo.mall.talent.exception.APIException;
import io.bumo.mall.talent.external.accountCenter.AccountCenterManager;
import io.bumo.mall.talent.external.accountCenter.resp.AccountCenterGetAccessTokenResp;
import io.bumo.mall.talent.external.wx.WechatManager;
import io.bumo.mall.talent.external.wx.resp.GetWxAccessTokenResp;
import io.bumo.mall.talent.service.GoodsCategoryService;
import io.bumo.mall.talent.service.SupplierInfoService;
import io.bumo.mall.talent.service.TalentService;
import io.bumo.mall.talent.service.UserInfoService;

public abstract class AbstractBaseBiz {

	RedisUtil redis = RedisUtil.getRu();

	@Autowired
	private AccountCenterManager accountCenterManager;
	@Autowired
	protected UserInfoService userInfoService;
	@Autowired
	protected WechatManager wechatManager;
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private SupplierInfoService supplierInfoService;
	@Autowired
	private TalentService talentService;

	@Value("${map.img.ip}")
	protected String mapImgIp;
	@Value("${file.upload.path}")
	protected String fileServerPath;

	protected String GOODS_EXPRESS_FEE = "0";
	protected Long GOODS_STOCK_ZERO = 0L;

	/**
	 * 校验请求入参
	 * 
	 * @param reqDto
	 * @throws Exception
	 */
	public void validateReqDto(Object reqDto) throws Exception {
		String err = BaseInfoValidator.validate(reqDto, false);
		if (!StringUtil.isEmpty(err)) {
			throw new APIException(ExceptionEnum.PARAME_ERROR, err);
		}
	}

	/**
	 * 获取账户中心accessToken
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccountCenterAccessToken() throws Exception {

		String accessToken = redis.get(ConstantsUtil.REDIS_ACCOUNT_CENTER_ACCESSTOKEN_TAG);
		if (Tools.isNull(accessToken)) {
			AccountCenterGetAccessTokenResp resp = accountCenterManager.getAccessToken();
			if (Tools.isNull(resp)) {
				throw new APIException(ExceptionEnum.GET_ACCOUNT_CENTER_ACCESSTOKEN_ERROR);
			}

			accessToken = resp.getAccessToken();

			redis.setex(ConstantsUtil.REDIS_ACCOUNT_CENTER_ACCESSTOKEN_TAG, accessToken,
					Integer.valueOf(resp.getExpiresIn()));
		}

		return accessToken;
	}

	/**
	 * 获取微信accessToken
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getWxAccessToken() throws Exception {

		String accessToken = redis.get(ConstantsUtil.REDIS_WX_ACCESSTOKEN_TAG);
		if (Tools.isNull(accessToken)) {
			GetWxAccessTokenResp getWxAccessTokenResp = wechatManager.getAccessToken();
			if (Tools.isNull(getWxAccessTokenResp)) {
				throw new APIException(ExceptionEnum.GET_WX_ACCESSTOKEN_ERROR);
			}

			accessToken = getWxAccessTokenResp.getAccess_token();

			redis.setex(ConstantsUtil.REDIS_WX_ACCESSTOKEN_TAG, accessToken,
					Integer.valueOf(getWxAccessTokenResp.getExpires_in()));
		}

		return accessToken;
	}

	/**
	 * 获取并检查用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws APIException
	 */
	public UserInfo checkUserInfoById(String userId) throws APIException {
		UserInfo userInfo = userInfoService.getUserInfoById(userId);
		if (userInfo == null)
			throw new APIException(ExceptionEnum.USER_INFO_NOT_EXIST);
		if (UserStateEnum.N.getCode() == userInfo.getState())
			throw new APIException(ExceptionEnum.USER_INFO_IS_FREEZE);
		return userInfo;
	}

	public UserInfo checkUserToken(String userToken) {

		String unionId = UserTokenJWTUtil.getUnionId(userToken);
		if (Tools.isNull(unionId)) {
			throw new APIException(ExceptionEnum.USER_TOKEN_ERR);
		}

		UserInfo userInfo = userInfoService.getUserInfoByWxUnionId(unionId);
		if (Tools.isNull(userInfo)) {
			throw new APIException(ExceptionEnum.USER_INFO_NOT_EXIST);
		}

		return userInfo;
	}

	/**
	 * 查询供货商信息
	 * 
	 * @param long1
	 * @return
	 */
	public SupplierInfo querySupplierInfoByGoodsCategoryId(Long categoryId) {
		GoodsCategory goodsCategory = goodsCategoryService.queryGoodsCategoryById(categoryId);
		if (Tools.isNull(goodsCategory)) {
			throw new APIException(ExceptionEnum.GOODS_CATEGORY_EXIST);
		}

		SupplierInfo supplierInfo = supplierInfoService.querySupplierInfoById(goodsCategory.getSupplierId());
		if (Tools.isNull(supplierInfo)) {
			throw new APIException(ExceptionEnum.GOODS_SUPPLIER_EXIST);
		}
		return supplierInfo;
	}

	/**
	 * 校验用户是否为当前达人店店主
	 * 
	 * @param reqDto
	 * @return
	 */
	public Boolean checkUserIdentity(String talentId, String userToken) {

		boolean isTalent = false;

		if (Tools.isNull(userToken) | Tools.isNull(talentId)) {
			isTalent = false;
		} else {
			UserInfo userInfo = checkUserToken(userToken);
			Talent talent = talentService.queryTalentByUserId(userInfo.getId());
			if (!Tools.isNull(talent)) {
				if (talentId.equals(talent.getId())) {
					isTalent = true;
				}
			}
		}
		return isTalent;
	}
	
	/**
	 * 获取请求IP
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
}
