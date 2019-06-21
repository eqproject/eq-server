package org.eq.modules.bc.service;

import lombok.RequiredArgsConstructor;
import org.eq.modules.bc.dao.InitiatorAccMapper;
import org.eq.modules.bc.entity.InitiatorAcc;
import org.eq.modules.bc.entity.InitiatorAccExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitiatorService {
	private final InitiatorAccMapper initiatorAccMapper;
	
	public List<InitiatorAcc> findInitiatorAccAll(){
		InitiatorAccExample example = new InitiatorAccExample();
		example.or().andTypeEqualTo(0);
		return initiatorAccMapper.selectByExample(example);
	}
	
	public InitiatorAcc findInitiatorByAddress(String address){
		InitiatorAccExample example = new InitiatorAccExample();
		example.or().andAddressEqualTo(address);
		List<InitiatorAcc> list =  initiatorAccMapper.selectByExample(example);
		return list==null||list.isEmpty()?null:list.get(0);
	}
	
	public InitiatorAcc queryMiddleAccountByType(Integer type){
		InitiatorAccExample example = new InitiatorAccExample();
		example.or().andTypeEqualTo(type);
		List<InitiatorAcc> list =  initiatorAccMapper.selectByExample(example);
		return list==null||list.isEmpty()?null:list.get(0);
	}
	
	public List<InitiatorAcc> queryMiddleAccount(){
		InitiatorAccExample example = new InitiatorAccExample();
		example.or().andTypeNotEqualTo(0);
		return initiatorAccMapper.selectByExample(example);
	}
	
	public void updateKeyStore(String address,String keyStore){
		//update initiator_acc set private_key=:2,update_time=now() where address=:1
		InitiatorAcc acc = new InitiatorAcc();
		acc.setKeyStore(keyStore);
		acc.setUpdateTime(new Date());

		InitiatorAccExample example0 = new InitiatorAccExample();
		example0.or().andAddressEqualTo(address);
		initiatorAccMapper.updateByExampleSelective(acc,example0);
	}
}
