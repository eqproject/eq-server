package org.eq.modules.bc.biz;

import org.eq.modules.bc.common.ConstantsUtil;
import org.eq.modules.bc.entity.InitiatorAcc;
import org.eq.modules.bc.service.InitiatorService;
import org.eq.modules.enums.BcAccountTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitiatorPoolBiz {

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private InitiatorService initiatorService;
	
	@Value("${pool.account.threshold}")
	private Integer initiatorPoolSize;

	private InitiatorAcc activityMiddleAcc;

	private InitiatorAcc buyMiddleAcc;

	private InitiatorAcc sellMiddleAcc;

	private InitiatorAcc transferMiddleAcc;

	private InitiatorAcc acceptMiddleAcc;

	@PostConstruct
	public void init(){
		activityMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.ACTIVITY.getCode());
		buyMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.BUY.getCode());
		sellMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.SELL.getCode());
		transferMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.TRANSFER.getCode());
		acceptMiddleAcc = initiatorService.queryMiddleAccountByType(BcAccountTypeEnum.ACCEPT.getCode());
	}

	
	public String getInitiator(){
		//当redis中的缓存账户数小于某个值时 需要重新插入
		int poolSize = redisTemplate.opsForSet().members(ConstantsUtil.INITIATOR_LIST_TAG).size();
		if(poolSize < initiatorPoolSize){
			loadData4RedisCache();
		}
		return (String) redisTemplate.opsForSet().pop(ConstantsUtil.INITIATOR_LIST_TAG);
	} 
	
	
	public void loadData4RedisCache(){
		List<InitiatorAcc> accList = initiatorService.findInitiatorAccAll();
		List<String> availableAccAddr = new ArrayList<String>();
		String accArr[] = {};
		for (InitiatorAcc acc : accList) {
			redisTemplate.opsForValue().set(ConstantsUtil.INITIATOR_SINGLE_TAG + acc.getAddress(),acc.getAddress());
			availableAccAddr.add(acc.getAddress());
		}
		String[] accArr1 = availableAccAddr.toArray(accArr);
		redisTemplate.opsForSet().add(ConstantsUtil.INITIATOR_LIST_TAG, accArr1);
	}
	
	public List<InitiatorAcc> findInitiatorAccAll(){
		return initiatorService.findInitiatorAccAll();
	}
	
	public InitiatorAcc getInitiatorInfo(String address){
		return initiatorService.findInitiatorByAddress(address);
	}

	public InitiatorAcc getActivityMiddleAcc() {
		return activityMiddleAcc;
	}

	public InitiatorAcc getBuyMiddleAcc() {
		return buyMiddleAcc;
	}

	public InitiatorAcc getSellMiddleAcc() {
		return sellMiddleAcc;
	}

	public InitiatorAcc getTransferMiddleAcc() {
		return transferMiddleAcc;
	}

	public InitiatorAcc getAcceptMiddleAcc() {
		return acceptMiddleAcc;
	}

	public void updateKeyStore(String address, String keyStore){
		initiatorService.updateKeyStore(address, keyStore);
	}
}
