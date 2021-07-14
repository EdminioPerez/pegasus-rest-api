package com.greek.service.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class MethodsLoggingAspect {

	@Around("methodsToBeProfiled()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		log.trace("Executing {} {}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());

		Object executed = pjp.proceed();

		log.trace("Finished {} {}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());

		return executed;
	}

	@Pointcut("within(com.greek..*)")
	public void methodsToBeProfiled() {
		// Used as method entry for the aspect
	}

}
