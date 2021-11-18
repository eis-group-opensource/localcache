/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bean holding the actual locally scoped caches. Not for direct use.
 * 
 * @author ggrazevicius
 */
public class LocalCacheHolder {
    
    private static final Logger logger = Logger.getLogger(LocalCacheHolder.class.getName());
    
    private final ThreadLocal<Map<String, Map<CacheKey, Object>>> localScopeCaches = new ThreadLocal<>();
    
    public Object get(String cacheName, Method method, Object[] args) {
        final Map<String, Map<CacheKey, Object>> localCaches = localScopeCaches.get();
        
        if (localCaches == null || !localCaches.containsKey(cacheName)) {
            logger.log(Level.SEVERE, "Non-existent cache was being accessed. Tried to retrieve value from ''{0}'' on method {1}", new Object[] {cacheName, method.toString()});
            return null;
        }
        
        return localCaches.get(cacheName).get(new CacheKey(method, args));
    }
    
    public void put(String cacheName, Method method, Object[] args, Object value) {
        Map<String, Map<CacheKey, Object>> localCaches = localScopeCaches.get();
        
        if (localCaches == null || !localCaches.containsKey(cacheName)) {
            logger.log(Level.SEVERE, "Non-existent cache was being accessed. Tried to put value to ''{0}'' on method {1}", new Object[] {cacheName, method.toString()});
            return;
        }
        
        localCaches.get(cacheName).put(new CacheKey(method, args), value);
    }
    
    public void createCache(String name) {
        initCache();
        final Map<String, Map<CacheKey, Object>> localCaches = localScopeCaches.get();
        
        createSingleCache(localCaches, name);
    }

    private void createSingleCache(final Map<String, Map<CacheKey, Object>> localCaches, String name) {
        if (!localCaches.containsKey(name)) {
            localCaches.put(name, new HashMap<CacheKey, Object>(100));
        }
    }
    
    public void createCaches(String[] names) {
        initCache();
        final Map<String, Map<CacheKey, Object>> localCaches = localScopeCaches.get();
        
        for (String name : names) {
            createSingleCache(localCaches, name);
        }
    }
    
    public void destroyCache(String name) {
        final Map<String, Map<CacheKey, Object>> localCaches = localScopeCaches.get();
        localCaches.remove(name);
    }
    
    public void destroyCaches(String[] names) {
        final Map<String, Map<CacheKey, Object>> localCaches = localScopeCaches.get();
        
        for (String name : names) {
            localCaches.remove(name);
        }
    }

    private void initCache() {
        if (localScopeCaches.get() == null) {
            localScopeCaches.set(new HashMap());
        }
    }
    
    private static class CacheKey {

        private final Method method;
        
        private final Object[] args;
        
        public CacheKey(Method method, Object[] args) {
            this.method = method;
            this.args = args;
        }

        public Method getMethod() {
            return method;
        }

        public Object[] getArgs() {
            return args;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 11 * hash + Objects.hashCode(this.method);
            hash = 11 * hash + Arrays.deepHashCode(this.args);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final CacheKey other = (CacheKey) obj;
            if (!Objects.equals(this.method, other.method)) {
                return false;
            }
            if (!Arrays.deepEquals(this.args, other.args)) {
                return false;
            }
            return true;
        }

        
        
    }
    
}
