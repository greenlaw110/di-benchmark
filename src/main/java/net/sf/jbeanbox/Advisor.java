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

import java.util.regex.Pattern;

// =============Advisor class begin===========
/**
 * @author Yong Zhu
 * @since 2016-2-13
 * @update 2016-08-23
 *
 */
class Advisor {// Advisor = Advice + Point-cut
	String classnameReg, methodNameReg, adviceMethodName;
	BeanBox adviceBeanBox;
	boolean isAOPAlliance = false;
	String adviceType;

	/**
	 * Create an Advisor
	 * 
	 * @param classnameReg
	 * @param methodNameReg
	 * @param adviceBeanBox
	 * @param adviceMethodName
	 * @param adviceType
	 * @param isAOPAlliance
	 */
	Advisor(String classnameReg, String methodNameReg, BeanBox adviceBeanBox, String adviceMethodName,
			String adviceType, boolean isAOPAlliance) {
		if (BeanBoxUtils.isEmptyStr(classnameReg) || BeanBoxUtils.isEmptyStr(methodNameReg) || adviceBeanBox == null
				|| BeanBoxUtils.isEmptyStr(adviceMethodName))
			throw new AssertionError("BeanBox create Advisor error! ClassNameReg:" + classnameReg + " methodNameReg:"
					+ methodNameReg + " beanbox:" + adviceBeanBox + " aroundMethodName:" + adviceMethodName);
		this.classnameReg = classnameReg;
		this.methodNameReg = methodNameReg;
		this.adviceBeanBox = adviceBeanBox;
		this.adviceMethodName = adviceMethodName;
		this.isAOPAlliance = isAOPAlliance;
		this.adviceType = adviceType;
	}

	/**
	 * Check if beanClassName and methodName match classnameReg and methodNameReg
	 */
	protected boolean match(String beanClassName, String methodName) {
		int i = beanClassName.indexOf("$$");
		if (i > 0)
			beanClassName = beanClassName.substring(0, i);
		return Pattern.compile(classnameReg).matcher(beanClassName).matches()
				&& Pattern.compile(methodNameReg).matcher(methodName).matches();
	}
}