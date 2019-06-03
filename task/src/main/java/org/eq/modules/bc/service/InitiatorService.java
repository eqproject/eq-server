package org.eq.modules.bc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.bumo.mall.talent.dao.InitiatorDAO;
import io.bumo.mall.talent.domain.InitiatorAcc;

public class InitiatorService {
	@Autowired
	private InitiatorDAO initiatorDAO;
	
	public List<InitiatorAcc> findInitiatorAccAll(){
		return initiatorDAO.findInitiatorAccAll();
	}
	
	public InitiatorAcc findInitiatorByAddress(String address){
		return initiatorDAO.queryInitiatorByAddress(address);
	}
	
	public InitiatorAcc queryMiddleAccountByType(Integer type){
		return initiatorDAO.queryMiddleAccByType(type);
	}
	
	public List<InitiatorAcc> queryMiddleAccount(){
		return initiatorDAO.queryMiddleAcc();
	}
	
	public void updateKeyStore(String address,String keyStore){
		initiatorDAO.updateKeyStore(address, keyStore);
	}
}
