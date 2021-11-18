/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache;

import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ggrazevicius
 */
public class LocalCacheScopeExecutionTemplateTest {
    private static final String VALUE = "strVal";
    private LocalCacheScopeExecutionTemplate cachedExecutionTemplate;
    private LocalCacheHolder localCacheHolder = new LocalCacheHolder();
    
    public LocalCacheScopeExecutionTemplateTest() {
    }
    
    @Before
    public void setUp() {
        cachedExecutionTemplate = new LocalCacheScopeExecutionTemplate();
        cachedExecutionTemplate.setLocalCacheHolder(localCacheHolder);
    }

    @Test
    public void cacheAccessFromTemplateWorks() throws Throwable {
        final Method method = LocalCacheScopeExecutionTemplateTest.class.getMethod("setUp");
        cachedExecutionTemplate.executeWithLocalCache(new String[] {"test"}, new LocalCacheScopeExecutionCallback() {

            @Override
            public Object execute() throws Throwable {
                localCacheHolder.put("test", method, null, VALUE);
                assertEquals(VALUE, localCacheHolder.get("test", method, null));
                return null;
            }
        });
        
        assertNull(localCacheHolder.get("test", method, null));
    }
    
}
