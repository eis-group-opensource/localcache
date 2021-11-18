/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache;

import java.lang.reflect.Method;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ggrazevicius
 */
public class LocalCacheHolderTest {
    
    private static final String VALUE = "strVal";
    
    private LocalCacheHolder localCacheHolder;
    
    public LocalCacheHolderTest() {
    }
    
    @Before
    public void setUp() {
        localCacheHolder = new LocalCacheHolder();
    }

    /**
     * Test of get method, of class LocalCacheHolder.
     */
    @Test
    public void cacheStorageWorks() throws NoSuchMethodException {
        Method method = LocalCacheHolderTest.class.getMethod("setUp");
        localCacheHolder.createCache("test");
        localCacheHolder.put("test", method, null, VALUE);
        assertEquals(VALUE, localCacheHolder.get("test", method, null));
        localCacheHolder.destroyCache("test");
    }
    
    @Test
    public void nonExistentCacheAccessIsImpossible() throws NoSuchMethodException {
        Method method = LocalCacheHolderTest.class.getMethod("setUp");
        localCacheHolder.put("test", method, null, VALUE);
        assertNull(localCacheHolder.get("test", method, null));
    }
    
}
