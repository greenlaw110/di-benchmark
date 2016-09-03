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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject BeanBox to a field or constructor
 * 
 * On field example:<br/>
 * @InjectBox(FieldBox.class, required=true) <br/>
 * Field someField;
 * 
 * On constructor example:<br/>
 * @InjectBox(s0="hello world", b2="false", box4=EBox.class) <br/>
 * public A(String s, B b, boolean b1, D d, E e){ ... }
 * 
 * currently only support String, boolean, Integer and Class type
 * 
 * @author Yong Zhu
 * @since 2016-2-13
 * @update 2016-08-31
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.CONSTRUCTOR })
public @interface InjectBox {
	public static String IMPOSSIBLE_STRING = "StuPid AnNotatioN";
	public static int IMPOSSIBLE_INT = -99819981;

	public Class<?> value() default Object.class;

	// Default if no BeanBox can be created, will throw an exception, set to false to disable
	public boolean required() default true;

	// Below are for constructor parameters, more than 6 parameters better use other configuration
	public Class<?> box0() default Object.class;// inject BeanBox

	public Class<?> box1() default Object.class;

	public Class<?> box2() default Object.class;

	public Class<?> box3() default Object.class;

	public Class<?> box4() default Object.class;

	public Class<?> box5() default Object.class;

	public String s0() default IMPOSSIBLE_STRING; // inject String, defalut is an impossible value

	public String s1() default IMPOSSIBLE_STRING;

	public String s2() default IMPOSSIBLE_STRING;

	public String s3() default IMPOSSIBLE_STRING;

	public String s4() default IMPOSSIBLE_STRING;

	public String s5() default IMPOSSIBLE_STRING;

	public int i0() default -99819981; // inject int

	public int i1() default -99819981;

	public int i2() default -99819981;

	public int i3() default -99819981;

	public int i4() default -99819981;

	public int i5() default -99819981;

	public String b0() default IMPOSSIBLE_STRING;// inject boolean

	public String b1() default IMPOSSIBLE_STRING;

	public String b2() default IMPOSSIBLE_STRING;

	public String b3() default IMPOSSIBLE_STRING;

	public String b4() default IMPOSSIBLE_STRING;

	public String b5() default IMPOSSIBLE_STRING;

	// TODO add byte, long, double, ...
}
