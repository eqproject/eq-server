package org.eq.modules.bc.common.log;

import io.bumo.mall.talent.common.log.Logger;

public final class LoggerFactory {
    private static final String EMPTY_PREFIX = null;

    public static Logger getLogger(Class<?> key) {
        return new Logger(EMPTY_PREFIX, org.slf4j.LoggerFactory.getLogger(key));
    }

    public static Logger getLogger(String key) {
        return new Logger(EMPTY_PREFIX, org.slf4j.LoggerFactory.getLogger(key));
    }

    public static Logger getLogger(String prefix, Class<?> key) {
        return new Logger(prefix, org.slf4j.LoggerFactory.getLogger(key));
    }
    public static Logger getLogger(String prefix, String key) {
        return new Logger(prefix, org.slf4j.LoggerFactory.getLogger(key));
    }
}
