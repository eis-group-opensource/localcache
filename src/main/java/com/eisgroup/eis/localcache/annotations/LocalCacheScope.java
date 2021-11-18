/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method execution as local cache context. Any method invoked from the
 * marked method will be executed in cached context. All cache names supplied
 * as annotation value will be accessible.
 * 
 * Annotation only works on Spring beans. All restrictions that apply to selected
 * aspect application method apply (for example it might only work on methods that are
 * public and part of an interface).
 * 
 * @see LocallyCacheable
 * @author ggrazevicius
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LocalCacheScope {
    
    /**
     * list of cache names to initialize and hold in the scope of this method
     * 
     * @return 
     */
    String[] value();
}
