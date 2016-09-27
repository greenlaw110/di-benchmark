package com.greenlaw110.di_benchmark.objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.BeanBox;
import com.github.drinkjava2.InjectBox;

import javax.inject.Inject;
import java.util.Objects;

@InjectBox(prototype = true) // for jBeanBox usage
@Component // for Spring usage
@Scope("prototype") // for Spring usage
public class D1 {

	@InjectBox // for jBeanBox
	@Inject // for JSR330 compliant DI libraries
	public D1(E e) {
	}

	@Override
	public int hashCode() {
		return Objects.hash("d1");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		return (obj instanceof D1);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	public static void main(String[] args) {
		BeanBox.getBean(D1.class);
	}
}
