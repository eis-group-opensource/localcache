/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Marks method as being locally cacheable. Locally cacheable methods
 * are cached within local cache scope. The scope is defined by annotating
 * methods with {@link LocalCacheScope}.
 * 
 * <ul>
 * <li>Annotated method should - be pure functions (meaning they do not change any
 * state, but only perform computations and return results).</li>
 * <li>All arguments to marked method must correctly implement hashCode() and equals(...) as
 * actual arguments passed are a part of cache key.</li>
 * </ul>
 * 
 * @author ggrazevicius
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LocallyCacheable {
    /**
     * Name of the cache to use
     * 
     * @return 
     */
    String value();
}
