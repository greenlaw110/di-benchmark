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
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * BeanBoxContext acts the same role like ApplicationContext in Spring
 * 
 * @author Yong Zhu
 * @since 2016-2-13
 * @update 2016-08-21
 *
 */
public class BeanBoxContext {
	// The default BoxIdentity is "Box", BoxIdentity will be use for looking for BeanBox class
	String boxIdentity = "Box";

	Boolean ignoreAnnotation = false; // if set true, will ignore @injectBox annotation

	// Advisors cache
	protected CopyOnWriteArrayList<Advisor> advisorList = new CopyOnWriteArrayList<Advisor>();

	// Singleton instance cache
	protected HashMap<String, Object> signletonCache = new HashMap<String, Object>();

	// Configuration class cache
	protected CopyOnWriteArrayList<Class<?>> configClassList = new CopyOnWriteArrayList<Class<?>>();

	// preDestory method cache
	protected ConcurrentHashMap<String, Method> preDestoryMethodCache = new ConcurrentHashMap<String, Method>();

	public BeanBoxContext(Class<?>... configClasses) {
		for (Class<?> configClass : configClasses) {
			configClassList.add(configClass);
		}
	}

	/**
	 * Set BoxIdentity, BoxIdentity will be use for looking for BeanBox class
	 */
	public BeanBoxContext setBoxIdentity(String boxIdentity) {
		this.boxIdentity = boxIdentity;
		return this;
	}

	public String getBoxIdentity() {
		return boxIdentity;
	}

	public <T> T getBean(Class<?> clazz) {
		return getBeanBox(null, clazz, null, null, this, true).getBean();
	}

	public <T> T getBean(Class<?> clazz, Class<?> configClass) {
		return getBeanBox(null, clazz, configClass, null, this, true).getBean();
	}

	public BeanBoxContext addConfig(Class<?> configClass) {
		configClassList.add(configClass);
		return this;
	}

	public Boolean getIgnoreAnnotation() {
		return ignoreAnnotation;
	}

	public BeanBoxContext setIgnoreAnnotation(Boolean ignoreAnnotation) {
		this.ignoreAnnotation = ignoreAnnotation;
		return this;
	}

	/**
	 * When close() method be called, call all preDestory() method for all singleTon Bean instances.
	 */
	public void close() {
		for (String beanID : preDestoryMethodCache.keySet()) {
			Object bean = signletonCache.get(beanID);
			Method method = preDestoryMethodCache.get(beanID);
			try {
				method.invoke(bean, new Object[] {});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		boxIdentity = "Box";
		advisorList = new CopyOnWriteArrayList<Advisor>();
		signletonCache = new HashMap<String, Object>();
		configClassList = new CopyOnWriteArrayList<Class<?>>();
		preDestoryMethodCache = new ConcurrentHashMap<String, Method>();
	}

	/**
	 * Set AOPAround, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAOPAround(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList
				.add(new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName, "AROUND", true));
	}

	/**
	 * Set AOPBefore, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAOPBefore(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList
				.add(new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName, "BEFORE", true));
	}

	/**
	 * Set AOPAfterReturning, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAOPAfterReturning(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList.add(new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName,
				"AFTERRETURNING", true));
	}

	/**
	 * Set AOPAfterThrowing, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAOPAfterThrowing(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList.add(
				new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName, "AFTERTHROWING", true));
	}

	/**
	 * Set AspectjAround, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAspectjAround(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList
				.add(new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName, "AROUND", false));
	}

	/**
	 * Set AspectjBefore, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAspectjBefore(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList
				.add(new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName, "BEFORE", false));
	}

	/**
	 * Set AspectjAfterReturning, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAspectjAfterReturning(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList.add(new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName,
				"AFTERRETURNING", false));
	}

	/**
	 * Set AspectjAfterThrowing, ClassNameReg and methodNameReg use java Regex
	 */
	public void setAspectjAfterThrowing(String classNameReg, String methodNameReg, BeanBox adviceBeanBox,
			String adviceAroundMethodName) {
		advisorList.add(new Advisor(classNameReg, methodNameReg, adviceBeanBox, adviceAroundMethodName, "AFTERTHROWING",
				false));
	}

	private static BeanBox wrapClassToBeanBox(Class<?> clazz, BeanBoxContext context) {
		return new BeanBox(clazz);
	}

	/**
	 * Find BeanBox class and create instance, for field with @InjectBox annotation, follow below order: <br/>
	 * Format: A.class{ @Inject(B.class) C fieldname;} <br/>
	 * 1) B.class (if is BeanBox)<br/>
	 * 2) B$CBox.class in B.class <br/>
	 * 3) B$FieldnameBox.class in B.class <br/>
	 * 
	 * Format: A.class{ @Inject C field; ...} <br/>
	 * 4) C.class (if is BeanBox)<br/>
	 * 5) CBox.class in same package of C <br/>
	 * 6) C$CBox.class in C.class <br/>
	 * 7) "ABox$CBox.class" in ABox.class <br/>
	 * 8) "ABox$FieldnameBox.class" in ABox.class <br/>
	 * 9) ConfigClass$CBox.class in globalConfig classes <br/>
	 * 10) ConfigClass$FieldnameBox.class in globalConfig classes <br/>
	 * 
	 * for a context.getBean(C.class) call, follow above #4, #5, #6, #9 order <br/>
	 * for a context.getBean(C.class, B.class) call, follow above #1, #2, #4, #5, #6, #9 order <br/>
	 * 
	 * If no BeanBox class found, if A.class has 0 parameter constructor or annotated constructor, wrap to BeanBox.<br/>
	 * if no BeanBox created at final, throw a error unless "required=false" set in @injectBox annotation
	 */
	@SuppressWarnings("unchecked")
	public static BeanBox getBeanBox(Class<?> ownerClass, Class<?> clazz, Class<?> annotationClass, String fieldName,
			BeanBoxContext context, boolean required) {
		if (Object.class.equals(annotationClass))
			annotationClass = null;
		Class<?> box = null;
		if (annotationClass != null) { // getBeanBox(A.class, B.class)
			if (BeanBox.class.isAssignableFrom(annotationClass))
				box = annotationClass;// #1
			if (box == null && clazz != null)
				box = BeanBoxUtils
						.checkIfExist(annotationClass.getName() + "$" + clazz.getSimpleName() + context.boxIdentity);// #2
			if (box == null)
				box = BeanBoxUtils.checkIfExist(annotationClass.getName() + "$"
						+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + context.boxIdentity);// #3
		} else {// getBeanBox(A.class)
			if (clazz == null)
				BeanBoxUtils.throwEX(null, "BeanBox getBeanBox error! target class not set");
			if (BeanBox.class.isAssignableFrom(clazz))
				box = clazz;// #4
			if (box == null)
				box = BeanBoxUtils.checkIfExist(clazz.getName() + context.boxIdentity);// #5
			if (box == null)
				box = BeanBoxUtils.checkIfExist(clazz.getName() + "$" + clazz.getSimpleName() + context.boxIdentity);// #6
			if (box == null && ownerClass != null)
				box = BeanBoxUtils.checkIfExist(
						ownerClass.getName() + context.boxIdentity + "$" + clazz.getSimpleName() + context.boxIdentity);// #7
			if (box == null && ownerClass != null && !BeanBoxUtils.isEmptyStr(fieldName))
				box = BeanBoxUtils.checkIfExist(
						ownerClass.getName() + context.boxIdentity + "$" + fieldName + context.boxIdentity);// #8
			if (box == null) {
				for (Class<?> configs : context.configClassList) {
					box = BeanBoxUtils
							.checkIfExist(configs.getName() + "$" + clazz.getSimpleName() + context.boxIdentity);// #9
					if (box != null)
						break;
					if (!BeanBoxUtils.isEmptyStr(fieldName))
						box = BeanBoxUtils
								.checkIfExist(configs.getName() + "$" + fieldName.substring(0, 1).toUpperCase()
										+ fieldName.substring(1) + context.boxIdentity);// #10
					if (box != null)
						break;
				}
			}
		}
		BeanBox beanbox;
		if (box == null)
			beanbox = wrapClassToBeanBox(clazz, context); // try wrap it to a BeanBox
		else
			beanbox = BeanBoxUtils.createBeanBoxInstance((Class<BeanBox>) box, context);
		if (required && beanbox == null)
			BeanBoxUtils.throwEX(null, "BeanBox getBeanBox error! class can not be created, class=" + clazz);
		if (beanbox.getClassOrValue() == null)
			beanbox.setClassOrValue(clazz);
		return beanbox;
	}
}