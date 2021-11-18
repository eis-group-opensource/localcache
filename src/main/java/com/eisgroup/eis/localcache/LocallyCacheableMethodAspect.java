/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache;

import com.eisgroup.eis.localcache.annotations.LocallyCacheable;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 *
 * @author ggrazevicius
 */
@Aspect
public class LocallyCacheableMethodAspect {
    
    private LocalCacheHolder localCacheHolder;
    
    @Pointcut("@annotation(com.eisgroup.eis.localcache.annotations.LocallyCacheable)")
    private void annotationPointcut() {};
    
    @Around("annotationPointcut()")
    public Object viewModelCacheableAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        LocallyCacheable localCache = method.getAnnotation(LocallyCacheable.class);
        
        if (localCache == null) {
            return joinPoint.proceed();
        } else {
            Object cachedValue = localCacheHolder.get(localCache.value(), method, joinPoint.getArgs());
            
            if (cachedValue != null) {
                return cachedValue;
            } else {
                cachedValue = joinPoint.proceed();
                localCacheHolder.put(localCache.value(), method, joinPoint.getArgs(), cachedValue);
                return cachedValue;
            }
        }
        
    }

    public void setLocalCacheHolder(LocalCacheHolder localCacheHolder) {
        this.localCacheHolder = localCacheHolder;
    }
    
}
