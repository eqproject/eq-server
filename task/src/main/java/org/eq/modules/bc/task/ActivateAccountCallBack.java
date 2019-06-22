package org.eq.modules.bc.task;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.enums.WalletStateEnum;
import org.eq.modules.wallet.dao.UserWalletMapper;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.entity.UserWalletExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ActivateAccountCallBack extends AbstractTaskCallBack {
    private static Logger logger = LoggerFactory.getLogger(ActivateAccountCallBack.class);

    @Autowired
    private UserWalletMapper userWalletMapper;

    public ActivateAccountCallBack() {
        super(BcTxRecordBizTypeEnum.ACTIVATE_ACCOUNT.getCode());
    }

    @Override
    public void success(String txId) {
        //获取钱包
        UserWallet uw = getUserWallet(txId);
        if (uw == null) {
            return;
        }
        boolean updateResult = updateUserWallet(uw, true);
        if (!updateResult) {
            logger.error("钱包激活成功,userId:{"+uw.getUserId()+"}");
        }
    }

    @Override
    public void fail(String txId) {
        //获取钱包
        UserWallet uw = getUserWallet(txId);
        if (uw == null) {
            return;
        }

        boolean updateResult = updateUserWallet(uw, false);
        if (!updateResult) {
            logger.error("钱包激活失败,userId:{"+uw.getUserId()+"}");
        }
    }

    private boolean updateUserWallet(UserWallet uw, boolean isSuccess) {
        int newStatus = isSuccess ? WalletStateEnum.ACTIVE.getState() : WalletStateEnum.NO_ACTIVE.getState();
        //更新用户钱包状态
        UserWalletExample example = new UserWalletExample();
        example.or().andUserIdEqualTo(uw.getUserId()).andStatusEqualTo(WalletStateEnum.NO_ACTIVE.getState());
        UserWallet userWallet = new UserWallet();
        userWallet.setStatus(newStatus);
        userWallet.setUpdateDate(new Date());
        int updateResult = userWalletMapper.updateByExampleSelective(userWallet, example);
        if (updateResult <= 0) {
            return false;
        }
        return true;
    }

    private UserWallet getUserWallet(String txId) {
        UserWallet userWallet = new UserWallet();

        UserWalletExample userWalletExample = new UserWalletExample();
        userWalletExample.or().andTxIdEqualTo(Long.parseLong(txId));
        List<UserWallet> result = userWalletMapper.selectByExample(userWalletExample);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }


}
