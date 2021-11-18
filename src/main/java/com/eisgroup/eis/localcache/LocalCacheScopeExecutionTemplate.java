/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache;

/**
 * Allows marking local cache scope, if for some reason {@link com.eisgroup.eis.localcache.annotations.LocalCacheScope} annotation
 * can not be used.
 * 
 * @author ggrazevicius
 */
public class LocalCacheScopeExecutionTemplate {
    
    private LocalCacheHolder localCacheHolder;
    
    public Object executeWithLocalCache(String[] cacheNames, LocalCacheScopeExecutionCallback callback) throws Throwable {
        localCacheHolder.createCaches(cacheNames);
        Object result = null;
        
        try {
            result = callback.execute();
        } finally {
            localCacheHolder.destroyCaches(cacheNames);
        }
        
        return result;
    }

    public void setLocalCacheHolder(LocalCacheHolder localCacheHolder) {
        this.localCacheHolder = localCacheHolder;
    }
    
}
