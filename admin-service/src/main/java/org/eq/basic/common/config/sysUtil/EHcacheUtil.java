package org.eq.basic.common.config.sysUtil;

import org.eq.basic.common.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import org.eq.basic.common.status.CacheKey;

@Component
public class EHcacheUtil {

    @Autowired
    private EhCacheCacheManager cacheManager;

    private static Logger logger = LoggerFactory.getLogger(EHcacheUtil.class);

    private static EHcacheUtil ehcacheUtil;

    private static final String SYS_CACHE = CacheKey.SYS_CACHE;

    public static synchronized  EHcacheUtil getInstance() {

        if (ehcacheUtil == null) {
            ehcacheUtil = (EHcacheUtil) SpringContextUtil.getBean("eHcacheUtil");
        }
        return ehcacheUtil;
    }

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public Object get(String key) {

        return this.get(SYS_CACHE, key);
    }

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Object get(String key, Object defaultValue) {

        Object value = this.get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 写入SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public void put(String key, Object value) {

        this.put(SYS_CACHE, key, value);
    }

    /**
     * 从SYS_CACHE缓存中移除
     *
     * @param key
     * @return
     */
    public void remove(String key) {

        this.remove(SYS_CACHE, key);
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    public Object get(String cacheName, String key) {

        Cache cache = this.cacheManager.getCache(cacheName);
        Object value = cache.get(key);
        if (value instanceof SimpleValueWrapper) {
            return ( (SimpleValueWrapper) value).get();
        }
        return value;
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @param defaultValue
     * @return
     */
    public Object get(String cacheName, String key, Object defaultValue) {

        this.cacheManager.getCache(cacheName);
        Object value = this.get(cacheName, key);
        return value != null ? value : defaultValue;
    }

    /**
     * 写入缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public void put(String cacheName, String key, Object value) {

        this.getCache(cacheName).put(key, value);
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    public void remove(String cacheName, String key) {

        this.getCache(cacheName).evict(key);
    }

    /**
     * 从缓存中移除所有
     *
     * @param cacheName
     */
    public void removeAll(String cacheName) {

        Cache cache = this.getCache(cacheName);
        cache.clear();
        logger.info("清理缓存： {}", cacheName);
    }

    /**
     * 获得一个Cache，没有则显示日志。
     *
     * @param cacheName
     * @return
     */
    private Cache getCache(String cacheName) {

         return this.cacheManager.getCache(cacheName);
    }

    public void setCacheManager(EhCacheCacheManager cacheManager) {

        this.cacheManager = cacheManager;
    }

}
