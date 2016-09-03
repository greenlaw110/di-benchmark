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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.cglib.proxy.Enhancer;

/**
 * Lots public static methods be moved into this BeanBoxUtils class
 * 
 * @author Yong Zhu
 * @since 2016-2-13
 * @update 2016-08-21
 * 
 */
public class BeanBoxUtils {
	private static ConcurrentHashMap<String, Integer> classExistCache = new ConcurrentHashMap<String, Integer>();

	/**
	 * Return true if empty or null
	 */
	public static boolean isEmptyStr(String str) {
		return (str == null || "".equals(str));
	}

	/**
	 * Search class by name
	 */
	public static Class<?> checkIfExist(String className) {
		Integer i = classExistCache.get(className);
		if (i == null)
			try {
				Class<?> clazz = Class.forName(className);
				if (BeanBox.class.isAssignableFrom((Class<?>) clazz)) {
					classExistCache.put(className, 1);
					return clazz;
				}
				classExistCache.put(className, 0);
				return null;
			} catch (Throwable e) {
				classExistCache.put(className, 0);
				return null;
			}
		if (1 == i) {
			try {
				return Class.forName(className);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Create an instance by its 0 parameter constructor
	 */
	public static Object createInstanceWithCtr0(Class<?> clazz) {
		try {
			Constructor<?> c0 = clazz.getDeclaredConstructor();
			c0.setAccessible(true);
			Object o = c0.newInstance();
			if (o instanceof BeanBox)
				throwEX(null, "BeanBox createInstanceWithCtr0 error:  clazz=" + clazz + " should not be a BeanBox");
			return o;
		} catch (Exception e) {
			throwEX(null, "BeanBox createInstanceWithCtr0 error: no 0 parameter constructor found! clazz=" + clazz);
		}
		return null;
	}

	/**
	 * Create a BeanBox instance by its 0 parameter constructor
	 */
	public static BeanBox createBeanBoxInstance(Class<BeanBox> clazz, BeanBoxContext context) {
		try {
			Constructor<?> c0 = clazz.getDeclaredConstructor();
			c0.setAccessible(true);
			BeanBox box = (BeanBox) c0.newInstance();
			box.setContext(context);
			return box;
		} catch (Exception e) {
			throwEX(null, "BeanBox createBeanBoxWithCtr0 error:  clazz=" + clazz);
		}
		return null;
	}

	static enum ObjectType {
		BeanBoxClass, BeanBoxInstance, Clazz, Instance
	}

	/**
	 * Judge unknow Object type
	 */
	public static ObjectType judgeType(Object unknowObject) {
		if (unknowObject instanceof BeanBox)
			return ObjectType.BeanBoxInstance;
		else if (unknowObject instanceof Class && BeanBox.class.isAssignableFrom((Class<?>) unknowObject))
			return ObjectType.BeanBoxClass;
		else if (unknowObject instanceof Class)
			return ObjectType.Clazz;
		return ObjectType.Instance;
	}

	@SuppressWarnings("unchecked")
	public static BeanBox buildBeanBoxWithAnotatedCtr(Class<?> clazz, BeanBoxContext context) {
		Constructor<?>[] cons = clazz.getDeclaredConstructors();
		for (Constructor<?> c : cons) {
			if (c.isAnnotationPresent(InjectBox.class)) {
				InjectBox a = c.getAnnotation(InjectBox.class);
				Class<?>[] parameterTypes = c.getParameterTypes();
				if (parameterTypes == null)
					return null;
				int parameterCount = parameterTypes.length;
				if (parameterCount == 0 || parameterCount > 6)
					BeanBoxUtils.throwEX(null,
							"BeanBox buildBeanBoxWithAnotatedCtr error, only support at most 6 constructor parameters,class="
									+ clazz);
				Object[] args = new Object[parameterCount];
				for (int i = 0; i < parameterCount; i++) {
					if (i == 0) {
						if (!Object.class.equals(a.box0())) {
							if (BeanBox.class.isAssignableFrom(a.box0()))
								args[i] = BeanBoxUtils.createBeanBoxInstance((Class<BeanBox>) a.box0(), context);
							else
								args[i] = new BeanBox(a.box0());
						} else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.s0()))
							args[i] = a.s0();
						else if (!new Integer(InjectBox.IMPOSSIBLE_INT).equals(a.i0()))
							args[i] = a.i0();
						else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.b0()))
							if ("true".equals(a.b0()))
								args[i] = true;
							else
								args[i] = false;
						else
							args[i] = parameterTypes[i];
					}
					if (i == 1) {
						if (!Object.class.equals(a.box1())) {
							if (BeanBox.class.isAssignableFrom(a.box1()))
								args[i] = BeanBoxUtils.createBeanBoxInstance((Class<BeanBox>) a.box1(), context);
							else
								args[i] = new BeanBox(a.box1());
						} else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.s1()))
							args[i] = a.s1();
						else if (!new Integer(InjectBox.IMPOSSIBLE_INT).equals(a.i1()))
							args[i] = a.i1();
						else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.b1()))
							if ("true".equals(a.b1()))
								args[i] = true;
							else
								args[i] = false;
						else
							args[i] = parameterTypes[i];
					}
					if (i == 2) {
						if (!Object.class.equals(a.box2())) {
							if (BeanBox.class.isAssignableFrom(a.box2()))
								args[i] = BeanBoxUtils.createBeanBoxInstance((Class<BeanBox>) a.box2(), context);
							else
								args[i] = new BeanBox(a.box2());
						} else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.s2()))
							args[i] = a.s2();
						else if (!new Integer(InjectBox.IMPOSSIBLE_INT).equals(a.i2()))
							args[i] = a.i2();
						else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.b2()))
							if ("true".equals(a.b2()))
								args[i] = true;
							else
								args[i] = false;
						else
							args[i] = parameterTypes[i];
					}
					if (i == 3) {
						if (!Object.class.equals(a.box3())) {
							if (BeanBox.class.isAssignableFrom(a.box3()))
								args[i] = BeanBoxUtils.createBeanBoxInstance((Class<BeanBox>) a.box3(), context);
							else
								args[i] = new BeanBox(a.box3());
						} else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.s3()))
							args[i] = a.s3();
						else if (!new Integer(InjectBox.IMPOSSIBLE_INT).equals(a.i3()))
							args[i] = a.i3();
						else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.b3()))
							if ("true".equals(a.b3()))
								args[i] = true;
							else
								args[i] = false;
						else
							args[i] = parameterTypes[i];
					}
					if (i == 4) {
						if (!Object.class.equals(a.box4())) {
							if (BeanBox.class.isAssignableFrom(a.box4()))
								args[i] = BeanBoxUtils.createBeanBoxInstance((Class<BeanBox>) a.box4(), context);
							else
								args[i] = new BeanBox(a.box4());
						} else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.s4()))
							args[i] = a.s4();
						else if (!new Integer(InjectBox.IMPOSSIBLE_INT).equals(a.i4()))
							args[i] = a.i4();
						else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.b4()))
							if ("true".equals(a.b4()))
								args[i] = true;
							else
								args[i] = false;
						else
							args[i] = parameterTypes[i];
					}
					if (i == 5) {
						if (!Object.class.equals(a.box5())) {
							if (BeanBox.class.isAssignableFrom(a.box5()))
								args[i] = BeanBoxUtils.createBeanBoxInstance((Class<BeanBox>) a.box5(), context);
							else
								args[i] = new BeanBox(a.box5());
						} else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.s5()))
							args[i] = a.s5();
						else if (!new Integer(InjectBox.IMPOSSIBLE_INT).equals(a.i5()))
							args[i] = a.i5();
						else if (!InjectBox.IMPOSSIBLE_STRING.equals(a.b5()))
							if ("true".equals(a.b5()))
								args[i] = true;
							else
								args[i] = false;
						else
							args[i] = parameterTypes[i];
					}
					if (args[i] instanceof Class && !BeanBox.class.isAssignableFrom((Class<?>) args[i]))
						args[i] = new BeanBox().setClassOrValue(args[i]);
				}
				BeanBox box = new BeanBox().setContext(context).setConstructor(clazz, args);
				return box;
			}
		}
		return null;
	}

	/**
	 * Use CGLib create proxy bean, if advice set for this class
	 */
	public static Object getProxyBean(Class<?> clazz, CopyOnWriteArrayList<Advisor> advisorList) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new ProxyBean(clazz, advisorList));
		return enhancer.create();
	}

	/**
	 * Transfer all Exceptions to RuntimeException. The only place throw Exception in this project
	 */
	public static void throwEX(Exception e, String errorMsg) throws AssertionError {
		if (e != null)// can log exception here, but I don't want import Log4j in this small tool
			e.printStackTrace();
		throw new RuntimeException(errorMsg);
	}

	/**
	 * Make the given field accessible, explicitly setting it accessible if necessary. The {@code setAccessible(true)}
	 * method is only called when actually necessary, to avoid unnecessary conflicts with a JVM SecurityManager (if
	 * active).
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if necessary. The {@code setAccessible(true)}
	 * method is only called when actually necessary, to avoid unnecessary conflicts with a JVM SecurityManager (if
	 * active).
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * Make the given constructor accessible, explicitly setting it accessible if necessary. The {@code
	 * setAccessible(true)} method is only called when actually necessary, to avoid unnecessary
	 */
	public static void makeAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
				&& !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}

	/**
	 * If found advice for this class, use CGLib to create proxy bean, CGLIB is the only way to create proxy to make
	 * source code simple.
	 */
	public static boolean ifHaveAdvice(CopyOnWriteArrayList<Advisor> advisors, Object classOrValue) {
		if (classOrValue == null || !(classOrValue instanceof Class))
			return false;
		Method[] methods = ((Class<?>) classOrValue).getMethods();
		for (Method method : methods)
			for (Advisor adv : advisors)
				if (adv.match(((Class<?>) classOrValue).getName(), method.getName()))
					return true;
		return false;
	}

	/**
	 * Only used for debug
	 */
	public static String debugInfo(Object[] args) {
		String s = "\r\n";
		for (int i = 0; i < args.length; i++)
			s += args[i] + "\r\n";
		return s;
	}
}
