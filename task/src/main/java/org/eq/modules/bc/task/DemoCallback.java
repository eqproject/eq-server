package org.eq.modules.bc.task;

import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;
import org.eq.modules.bc.service.BcTaskCallBack;

/**
 * * @author gb
 *
 * @version 2019/6/10
 */
public class DemoCallback {

    public static void main(String[] args) {
        new BcTaskCallBack(BcTxRecordBizTypeEnum.ACTIVATE_ACCOUNT,new DemoCallback()){

            @Override
            public void success(String txId) {
                //根据txId更新结果
            }

            @Override
            public void fail(String txId) {
                //根据txId更新结果
            }
        };
    }
}
