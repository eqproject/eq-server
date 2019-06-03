package org.eq.modules.bc.external.accountCenter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eq.modules.bc.common.util.HttpKit;
import org.eq.modules.bc.enums.ExceptionEnum;
import org.eq.modules.bc.external.accountCenter.resp.AccCenterSignBlobResp;
import org.eq.modules.bc.external.accountCenter.resp.AccountCenterGetAccessTokenResp;
import org.eq.modules.bc.external.accountCenter.resp.AccountCenterRegisterResp;
import org.eq.modules.bc.external.accountCenter.resp.AccountCenterSignBlobListResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;

import io.bumo.mall.talent.common.log.Logger;
import io.bumo.mall.talent.common.log.LoggerFactory;
import io.bumo.mall.talent.common.util.HttpKit;
import io.bumo.mall.talent.dto.resp.DataResp;
import io.bumo.mall.talent.enums.ExceptionEnum;
import io.bumo.mall.talent.external.accountCenter.resp.AccCenterSignBlobResp;
import io.bumo.mall.talent.external.accountCenter.resp.AccountCenterGetAccessTokenResp;
import io.bumo.mall.talent.external.accountCenter.resp.AccountCenterRegisterResp;
import io.bumo.mall.talent.external.accountCenter.resp.AccountCenterSignBlobListResp;

public class AccountCenterManager {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountCenterManager.class);
	
	@Value("${account.center.ip}")
	private String ipUrl;
	@Value("${account.center.appId}")
	private String appId;
	@Value("${account.center.appKey}")
	private String appKey;
	
	/**
	 * 通过unionId注册账户
	 * @param unionId
	 * @return
	 */
	public String register(String unionId, String accessToken){
		
		String url = ipUrl+"/account/v2/register";
		AccountCenterRegisterResp accountCenterRegisterResp = new AccountCenterRegisterResp();
		try{
			Map<String,String> map = new HashMap<String, String>();
			map.put("accessToken", accessToken);
			map.put("accountName", unionId);
			
			String result = HttpKit.post(url, JSON.toJSONString(map));
			logger.info("账户中心注册响应参数：",result);
			
			DataResp<Object> dataResp = JSON.parseObject(result, DataResp.class);
			if(ExceptionEnum.SUCCESS.getCode().equals(dataResp.getErrCode()) | ExceptionEnum.PROCESS.getCode().equals(dataResp.getErrCode())){
				accountCenterRegisterResp = JSON.parseObject(JSON.toJSONString(dataResp.getData()), AccountCenterRegisterResp.class);
				return accountCenterRegisterResp.getAddress();
			}else{
				logger.error("获取账户中心地址异常：",dataResp.getErrCode(),dataResp.getMsg());
			}
		}catch(Exception e){
			logger.error("获取账户中心地址异常",e);
		}
		return null;
	}
	
	/**
	 * 获取账户中心的accessToken
	 * @return
	 * @throws Exception
	 */
	public AccountCenterGetAccessTokenResp getAccessToken() throws Exception {
		
		String url = ipUrl+"/auth/accessToken";
		
		AccountCenterGetAccessTokenResp resp = new AccountCenterGetAccessTokenResp();
		
		Map<String, String> params = new HashMap<>();
		params.put("appId", appId);
		params.put("appKey", appKey);
		
		try{
			String result = HttpKit.post(url, JSON.toJSONString(params));
			DataResp<Object> dataResp = JSON.parseObject(result, DataResp.class);
			if(ExceptionEnum.SUCCESS.getCode().equals(dataResp.getErrCode())) {
				resp = JSON.parseObject(JSON.toJSONString(dataResp.getData()), AccountCenterGetAccessTokenResp.class);
				return resp;
			}
		}catch(Exception e){
			logger.error("获取账户中心accessToken异常",e);
		}
		return null;
	}
	
	public AccCenterSignBlobResp signBlob(String address, String blob, String accessToken){
		String url = ipUrl+"/sign/blob";
		AccCenterSignBlobResp accCenterSignBlobResp = null;
		try{
			Map<String,String> map = new HashMap<String, String>();
			map.put("accessToken", accessToken);
			map.put("address", address);
			map.put("blob", blob);
			String result = HttpKit.post(url, JSON.toJSONString(map));
			logger.info("调用账户中心签名返回：",result);
			
			DataResp<Object> dataResp = JSON.parseObject(result, DataResp.class);
			if(ExceptionEnum.SUCCESS.getCode().equals(dataResp.getErrCode())){
				accCenterSignBlobResp = JSON.parseObject(JSON.toJSONString(dataResp.getData()), AccCenterSignBlobResp.class);
			}else{
				logger.error("账户中心签名异常：",dataResp.getErrCode(),dataResp.getMsg());
			}
		}catch(Exception e){
			logger.error("账户中心签名异常");
			logger.error(e.getMessage(),e);
		}
		return accCenterSignBlobResp;
	}
	
	
	public AccountCenterSignBlobListResp signBlobList(HashSet<String> addressList, String blob, String accessToken){
		String url = ipUrl+"/sign/blob/list";
		AccountCenterSignBlobListResp resp = null;
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("accessToken", accessToken);
			map.put("addressList", addressList);
			map.put("blob", blob);
			String result = HttpKit.post(url, JSON.toJSONString(map));
			
			DataResp<Object> dataResp = JSON.parseObject(result, DataResp.class);
			if(ExceptionEnum.SUCCESS.getCode().equals(dataResp.getErrCode())){
				resp = JSON.parseObject(JSON.toJSONString(dataResp.getData()), AccountCenterSignBlobListResp.class);
			}else{
				logger.error("账户中心签名异常：",dataResp.getErrCode(),dataResp.getMsg());
			}
		}catch(Exception e){
			logger.error("账户中心签名异常");
			logger.error(e.getMessage(),e);
		}
		return resp;
	}
	
}