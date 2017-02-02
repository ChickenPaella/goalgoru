package com.gorugoru.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 * 컨트롤러 advice
 * 참고: class 대신 aspect 로 지정시 @AfterReturning returning NOT Working
 * @author Administrator
 *
 */
@Aspect
@Component
public class RequestAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestAspect.class);
	
	@Before("requestMapping()")
	public void beforeController(JoinPoint joinPoint) {
		logger.info("beforeController() Method: " + joinPoint.getSignature().getName());
		
		for(Object arg : joinPoint.getArgs()){
			if (arg instanceof ModelMap) {
				ModelMap modelMap = (ModelMap) arg;
				modelMap.clear();
			}
		}
	}
	
	@After("requestMapping()")
	public void afterController(JoinPoint joinPoint) {
		logger.info("afterController() Method: " + joinPoint.getSignature().getName());
		
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof ModelMap) {
				ModelMap model = (ModelMap) arg;
				if (!model.containsAttribute("status")) {
					model.addAttribute("status", 0);
				}
			}
		}
	}
    
	@AfterReturning(pointcut = "requestMapping()", returning = "retVal")
	public void afterControllerReturning(JoinPoint joinPoint, Object retVal) {
		logger.info("afterControllerReturning() Method: " + joinPoint.getSignature().getName());
		logger.info("Method returned value is : " + retVal);
	}
	
	@Pointcut("(controller() || restController()) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMapping() {}
	
	@Pointcut("execution(public * com..controller..*Controller.*(..)) && @target(org.springframework.stereotype.Controller)")
	public void controller() {}
	
	@Pointcut("execution(public * com..controller..*Controller.*(..)) && @target(org.springframework.web.bind.annotation.RestController)")
	public void restController() {}
}
