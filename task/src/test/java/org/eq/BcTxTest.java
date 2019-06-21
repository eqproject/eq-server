package org.eq;

import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.init.KeyStoreManager;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.bc.service.BcTxService;
import org.eq.modules.bc.task.HandleBcTxTransferJob;
import org.eq.modules.bc.task.HandleQueryTransferResultJob;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : gb 2019/6/19
 */
public class BcTxTest extends BaseTest{
    @Autowired
    private HandleBcTxTransferJob job1;
    @Autowired
    private HandleQueryTransferResultJob job2;
    @Autowired
    private BcTxService bcTxService;

    @Test
    public void test(){
        KeyStoreManager.setKeyStorePwd("123456");
        job1.execute();
    }

    @Test
    public void test2(){
        KeyStoreManager.setKeyStorePwd("123456");
        job2.execute();
    }

    @Test
    public void test3(){
        String hash = "634444779994f3ca2eaa23c3fa9639033ed0deea0ccc5045dec21b1b24a02281";
        List<BcTxRecord> bcTxRecordlist  = bcTxService.queryBcTxRecord4Hash(hash);
        for(BcTxRecord bcTxRecord : bcTxRecordlist){
            Integer bizType = bcTxRecord.getBizType();
            String txId = String.valueOf(bcTxRecord.getId());
            AbstractTaskCallBack.get(bizType).success(txId);
        }
    }
}
