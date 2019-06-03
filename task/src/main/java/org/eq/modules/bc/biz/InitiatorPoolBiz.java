package org.eq.modules.bc.biz;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.bumo.mall.talent.common.ConstantsUtil;
import io.bumo.mall.talent.common.util.RedisUtil;
import io.bumo.mall.talent.domain.InitiatorAcc;
import io.bumo.mall.talent.enums.BcAccountTypeEnum;
import io.bumo.mall.talent.service.InitiatorService;

public class InitiatorPoolBiz {
	
	private RedisUtil redis = RedisUtil.getRu();
	@Autowired
	private InitiatorService initiatorService;
	
	@Value("${pool.account.threshold}")
	private Integer initiatorPoolSize;
	
	private InitiatorAcc buyMiddleAcc;
	
	private InitiatorAcc refundMiddleAcc;
	
	private InitiatorAcc pickUpMiddleAcc;
	
	private InitiatorAcc withdrawMiddleAcc;
	
	private InitiatorAcc rewardMiddleAcc;
	
	private InitiatorAcc activityMiddleAcc;
	
	private InitiatorAcc activityRecycleAcc;
	
	@PostConstruct
	public void init(){
		buyMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.BUY.getCode());
		refundMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.REFUND.getCode());
		pickUpMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.PICK_UP.getCode());
		withdrawMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.WITHDRAW.getCode());
		rewardMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.REWARD.getCode());
		activityMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.ACTIVITY.getCode());
		activityRecycleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.COUPON_RECYCLE.getCode());
	}
	
	public String getInitiator(){
		//当redis中的缓存账户数小于某个值时 需要重新插入
		int poolSize = redis.smembers(ConstantsUtil.INITIATOR_LIST_TAG).size();
		if(poolSize < initiatorPoolSize){
			loadData4RedisCache();
		}
		return redis.spop(ConstantsUtil.INITIATOR_LIST_TAG);
	} 
	
	
	public void loadData4RedisCache(){
		List<InitiatorAcc> accList = initiatorService.findInitiatorAccAll();
		List<String> availableAccAddr = new ArrayList<String>();
		String accArr[] = {};
		for (InitiatorAcc acc : accList) {
			redis.set(ConstantsUtil.INITIATOR_SINGLE_TAG + acc.getAddress(),acc.getAddress());
			availableAccAddr.add(acc.getAddress());
		}
		String[] accArr1 = availableAccAddr.toArray(accArr);
		redis.sadd(ConstantsUtil.INITIATOR_LIST_TAG, accArr1);
	}
	
	public List<InitiatorAcc> findInitiatorAccAll(){
		return initiatorService.findInitiatorAccAll();
	}
	
	public InitiatorAcc getInitiatorInfo(String address){
		return initiatorService.findInitiatorByAddress(address);
	}
	
	public InitiatorAcc getBuyMiddleInfo(){
		return buyMiddleAcc;
	}
	
	public InitiatorAcc getRefundMiddleInfo(){
		return refundMiddleAcc;
	}
	public InitiatorAcc getPickUpMiddleInfo(){
		return pickUpMiddleAcc;
	}
	
	public InitiatorAcc getWithdrawMiddleInfo(){
		return withdrawMiddleAcc;
	}
	
	public InitiatorAcc getRewardMiddleInfo(){
		return rewardMiddleAcc;
	}
	
	public InitiatorAcc getActivityMiddleInfo(){
		return activityMiddleAcc;
	}
	
	public InitiatorAcc getActivityRecycleInfo(){
		return activityRecycleAcc;
	}
	
	public void updateKeyStore(String address,String keyStore){
		initiatorService.updateKeyStore(address, keyStore);
	}
}
