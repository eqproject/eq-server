/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.service;

import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.entity.OrderTradeExample;
import org.eq.basic.common.base.ServiceExtend;

/**
 * 订单交易Service
 * @author yufei.sun
 * @version 1.0
 */
public interface OrderTradeService extends ServiceExtend<OrderTrade,OrderTradeExample> {

    /**
     * 重试放券
     * @param tradeId
     * @param sysUser
     * @return
     */
    boolean voucherTrade(long tradeId, SysUser sysUser);

}