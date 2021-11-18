/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package com.eisgroup.eis.localcache;

import com.eisgroup.eis.localcache.annotations.LocalCacheScope;
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
public class LocalCacheScopeAspect {
    
    private LocalCacheScopeExecutionTemplate locallyCachedExecutionTemplate;
    
    @Pointcut("@annotation(com.eisgroup.eis.localcache.annotations.LocalCacheScope)")
    private void annotationPointcut() {};
    
    @Around("annotationPointcut()")
    public Object viewModelCacheableAdvice(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        LocalCacheScope localCacheScope = method.getAnnotation(LocalCacheScope.class);
        
        if (localCacheScope == null) {
            throw new IllegalStateException("Local cache adfvice is defined around method not annotated with @LocalCacheScope");
        }
        
        return locallyCachedExecutionTemplate.executeWithLocalCache(localCacheScope.value(), new LocalCacheScopeExecutionCallback() {

            @Override
            public Object execute() throws Throwable {
                return joinPoint.proceed();
            }
        });
        
    }

    public void setLocallyCachedExecutionTemplate(LocalCacheScopeExecutionTemplate locallyCachedExecutionTemplate) {
        this.locallyCachedExecutionTemplate = locallyCachedExecutionTemplate;
    }
    
    class ReturnInfo {
        public Object result;

        public Throwable exception;
    }
    
}
