package org.eq.modules.bc.service;

import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * * @author gb
 *
 * @version 2019/6/10
 */
public abstract class BcTaskCallBack {
    private static Map<Integer, Object> instances = new HashMap<>();

    public BcTaskCallBack(BcTxRecordBizTypeEnum typeEnum, Object instance) {
        this.instances.put(typeEnum.getCode(), instance);
    }

    public static Object getInstance(Integer type) {
        return instances.get(type);
    }

    protected abstract void success(String txId);

    protected abstract void fail(String txId);
}
