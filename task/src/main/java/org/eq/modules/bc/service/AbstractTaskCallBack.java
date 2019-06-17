package org.eq.modules.bc.service;

import java.util.HashMap;
import java.util.Map;

/**
 * * @author gb
 *
 * @version 2019/6/10
 */
public abstract class AbstractTaskCallBack {
    private static Map<Integer, AbstractTaskCallBack> instances = new HashMap<>();

    public AbstractTaskCallBack(int type){
        instances.put(type,this);
    }

    public static AbstractTaskCallBack get(int type){
        return instances.get(type);
    }

    public abstract void success(String txId);

    public abstract void fail(String txId);
}
