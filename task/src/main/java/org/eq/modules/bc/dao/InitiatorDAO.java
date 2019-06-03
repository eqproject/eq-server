package org.eq.modules.bc.dao;

import java.util.List;

import io.bumo.mall.talent.domain.InitiatorAcc;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog= "dataSource" )
public interface InitiatorDAO {
	
	@SQL("select id,address,public_key,private_key from initiator_acc where type =0")
	public List<InitiatorAcc> findInitiatorAccAll();
	
	@SQL("insert into initiator_acc(address,public_key,private_key,status,create_time,update_time) "
			+ "values (:1.address,:1.publicKey,:1.privateKey,:1.status,now(),now())")
	public boolean addInitiator(InitiatorAcc acc);
	
	@SQL("select address,public_key,private_key from initiator_acc where address=:1")
	public InitiatorAcc queryInitiatorByAddress(String address);
	
	@SQL("select address,public_key,private_key from initiator_acc where type =:1")
	public InitiatorAcc queryMiddleAccByType(Integer type);
	
	@SQL("select address,public_key,private_key from initiator_acc where type !=0")
	public List<InitiatorAcc> queryMiddleAcc();
	
	@SQL("update initiator_acc set private_key=:2,update_time=now() where address=:1")
	public boolean updateKeyStore(String address, String keyStore);
	
	
	
}
