package com.greek.service.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(matchIfMissing = false, prefix = "app.logging", value = "log-stack-methods", havingValue = "true")
public class StackMethodsLoggingAspect {

	@Around("stackMethodsToBeProfiled()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		log.trace("Executing {} {}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());

		Object executed = pjp.proceed();

		log.trace("Finished {} {}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());

		return executed;
	}

	@Pointcut("within(com.greek..*)")
	public void stackMethodsToBeProfiled() {
		// Used as method entry for the aspect
	}

}
