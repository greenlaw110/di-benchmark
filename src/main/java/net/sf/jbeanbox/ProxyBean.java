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

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ProxyBean created by CGLib
 * 
 * @author Yong Zhu
 * @since 2016-2-13
 * @update 2016-08-21
 *
 */
class ProxyBean implements MethodInterceptor {
	protected CopyOnWriteArrayList<Advisor> myAdvisors = new CopyOnWriteArrayList<Advisor>();

	protected ProxyBean(Class<?> clazz, CopyOnWriteArrayList<Advisor> globalAdvicors) {
		String beanClassName = clazz.getName();
		int i = beanClassName.indexOf("$$");// If created by CGLib, use the original class name as bean ID
		if (i > 0)
			beanClassName = beanClassName.substring(0, i);
		for (Advisor advisor : globalAdvicors) {// Make a copy from global advisors which only belong to this Bean
			Method[] methods = ((Class<?>) clazz).getMethods();
			for (Method method : methods)
				if (advisor.match(beanClassName, method.getName())) {
					myAdvisors.add(advisor);
					break;
				}
		}
	}

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy cgLibMethodProxy) throws Throwable {
		if (myAdvisors.size() > 0 && myAdvisors.get(0).match(obj.getClass().getName(), method.getName()))
			// Start a advice chain call
			return new AdviceCaller(this, obj, method, args, cgLibMethodProxy, myAdvisors).callNextAdvisor();
		else
			return cgLibMethodProxy.invokeSuper(obj, args);
	}
}