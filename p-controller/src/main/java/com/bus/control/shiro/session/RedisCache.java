package com.bus.control.shiro.session;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * Created by wwz on 2018-11-20.
 */
public class RedisCache<K,V> implements Cache<K,V> {
    private String keyPrefix = "shiro_redis_cache";

    @Override
    public Object get(Object key) throws CacheException {
        return null;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        return null;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}