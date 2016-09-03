
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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;

/**
 * JoinPoint interface detail see AspectJ doc, here is a brief implementation
 * 
 * @author Yong Zhu
 * @since 2016-2-13
 * @update 2016-08-23
 */
class AspectjJoinPoint implements JoinPoint {
	protected Object proxy;
	protected Object target;
	protected Method method;
	protected Signature signature;
	protected Object[] arguments;
	protected AdviceCaller caller;

	protected AspectjJoinPoint(Object proxyBean, Object target, Method method, Object[] arguments,
			AdviceCaller caller) {
		this.proxy = proxyBean;
		this.target = target;
		this.method = method;
		this.arguments = arguments;
		this.caller = caller;
	}

	public Object[] getArgs() {
		return arguments;
	}

	public String getKind() {
		return ProceedingJoinPoint.METHOD_EXECUTION;
	}

	public Signature getSignature() {
		if (this.signature == null)
			this.signature = new MethodSignatureImpl();
		return signature;
	}

	public SourceLocation getSourceLocation() {
		throw new UnsupportedOperationException();
	}

	public StaticPart getStaticPart() {
		throw new UnsupportedOperationException();
	}

	public Object getTarget() {
		return target;
	}

	public Object getThis() {
		return proxy;
	}

	public String toLongString() {
		return this.getClass().getName();
	}

	public String toShortString() {
		return this.getClass().getName();
	}

	protected class MethodSignatureImpl implements MethodSignature {// MethodSignature is required by Aspectj

		public String getName() {
			return method.getName();
		}

		public int getModifiers() {
			return method.getModifiers();
		}

		public Class<?> getDeclaringType() {
			return method.getDeclaringClass();
		}

		public String getDeclaringTypeName() {
			return method.getDeclaringClass().getName();
		}

		public Class<?> getReturnType() {
			return method.getReturnType();
		}

		public Method getMethod() {
			return method;
		}

		public Class<?>[] getParameterTypes() {
			return method.getParameterTypes();
		}

		public String[] getParameterNames() {// just throw unsupported exception
			throw new UnsupportedOperationException();
		}

		public Class<?>[] getExceptionTypes() {
			return method.getExceptionTypes();
		}

		public String toShortString() {
			return method.getName();
		}

		public String toLongString() {
			return method.getName();
		}
	}
}