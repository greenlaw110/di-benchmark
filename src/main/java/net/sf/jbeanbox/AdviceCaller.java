/**
 * Copyright (C) 2016 Yong Zhu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.jbeanbox;

import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Yong Zhu
 * @since 2016-2-13
 * @update 2016-08-23
 *
 */
class AdviceCaller {
	protected final Object proxy;
	protected final Object target;
	protected final Method method;
	protected Object[] args;
	private MethodProxy cgLibMethodProxy;
	CopyOnWriteArrayList<Advisor> myAdvisors;
	protected int currentAdvisorIndex = -1;

	/**
	 * Create an AdviceCaller
	 * 
	 * @param proxyBean
	 * @param target
	 * @param method
	 * @param arguments
	 * @param cgLibMethodProxy
	 * @param myAdvisors
	 */
	protected AdviceCaller(Object proxyBean, Object target, Method method, Object[] arguments,
			MethodProxy cgLibMethodProxy, CopyOnWriteArrayList<Advisor> myAdvisors) {
		this.proxy = proxyBean;
		this.target = target;
		this.method = method;
		this.args = arguments;
		this.myAdvisors = myAdvisors;
		this.cgLibMethodProxy = cgLibMethodProxy;
	}

	/**
	 * Check and run the next advisor, first one no need check because already checked
	 */
	public Object callNextAdvisor() throws Throwable {
		if (this.currentAdvisorIndex >= this.myAdvisors.size() - 1)
			return cgLibMethodProxy.invokeSuper(target, args);
		Advisor advisor = myAdvisors.get(++this.currentAdvisorIndex);
		if (currentAdvisorIndex == 0 || advisor.match(target.getClass().getName(), method.getName())) {
			Object advice = advisor.adviceBeanBox.getBean();
			if (advisor.isAOPAlliance) {// AOP alliance type advice
				if ("AROUND".equals(advisor.adviceType)) {
					// public Object doAround(MethodInvocation caller) throws Throwable, AOP alliance & Spring's
					// around advice
					Method m = advice.getClass().getMethod(advisor.adviceMethodName,
							new Class[] { MethodInvocation.class });
					return m.invoke(advice, new AopAllianceInvocation(target, method, args, this));
				} else if ("BEFORE".equals(advisor.adviceType)) {
					// public void before(Method method, Object[] args, Object target) throws Throwable
					Method m = advice.getClass().getMethod(advisor.adviceMethodName,
							new Class[] { Method.class, Object[].class, Object.class });
					m.invoke(advice, new Object[] { method, args, target });
					return callNextAdvisor();
				} else if ("AFTERRETURNING".equals(advisor.adviceType)) {
					// public void afterReturning(Object result, Method method, Object[] args, Object target) throws
					// Throwable {}
					Object result = callNextAdvisor();
					Method m = advice.getClass().getMethod(advisor.adviceMethodName,
							new Class[] { Object.class, Method.class, Object[].class, Object.class });
					m.invoke(advice, new Object[] { result, method, args, target });
					return result;
				} else if ("AFTERTHROWING".equals(advisor.adviceType)) {
					// public void afterThrowing(Method method, Object[] args, Object target, Exception ex)
					// Detai see org.springframework.aop.ThrowsAdvice, here only implemented 4 arguments
					try {
						return callNextAdvisor();
					} catch (Exception ex) {
						Method m = advice.getClass().getMethod(advisor.adviceMethodName,
								new Class[] { Method.class, Object[].class, Object.class, Exception.class });
						m.invoke(advice, new Object[] { method, args, target, ex });
						throw ex;
					}
				}
			} else {// else is AspectJ advice, you can add your customized methods at here
				if ("AROUND".equals(advisor.adviceType)) {
					// public Object methodName(ProceedingJoinPoint caller) throws Throwable
					Method m = advice.getClass().getMethod(advisor.adviceMethodName,
							new Class[] { ProceedingJoinPoint.class });
					return m.invoke(advice, new AspectjProceedingJoinPoint(proxy, target, method, args, this));
				} else if ("BEFORE".equals(advisor.adviceType)) {
					// public void before(JoinPoint caller) throws Throwable
					Method m = advice.getClass().getMethod(advisor.adviceMethodName, new Class[] { JoinPoint.class });
					m.invoke(advice, new Object[] { new AspectjJoinPoint(proxy, target, method, args, this) });
					return callNextAdvisor();
				} else if ("AFTERRETURNING".equals(advisor.adviceType)) {
					// public void afterReturning(JoinPoint caller, Object result) throws Throwable
					Object result = callNextAdvisor();
					Method m = advice.getClass().getMethod(advisor.adviceMethodName,
							new Class[] { JoinPoint.class, Object.class });
					m.invoke(advice, new Object[] { new AspectjJoinPoint(proxy, target, method, args, this), result });
					return result;
				} else if ("AFTERTHROWING".equals(advisor.adviceType)) {
					// public void afterThrowing(JoinPoint caller, Exception ex) throws Throwable
					try {
						return callNextAdvisor();
					} catch (Exception ex) {
						Method m = advice.getClass().getMethod(advisor.adviceMethodName,
								new Class[] { JoinPoint.class, Exception.class });
						m.invoke(advice, new Object[] { new AspectjJoinPoint(proxy, target, method, args, this), ex });
						throw ex;
					}
				}
			}
			throw new AssertionError("BeanBox AdviceType not support error: " + advisor.adviceType);
		} else
			return callNextAdvisor();
	}
}