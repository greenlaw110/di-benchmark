package com.greenlaw110.di_benchmark.objects;

import java.util.Objects;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.BeanBox;
import com.github.drinkjava2.InjectBox;

@InjectBox(prototype = true)
@Component
@Scope("prototype")
public class D2 {
	private final E e;

	@InjectBox
	@Inject
	public D2(E e) {
		this.e = e;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		D2 d2 = (D2) o;
		return Objects.equals(e, d2.e);
	}

	@Override
	public int hashCode() {
		return Objects.hash(e);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	public static void main(String[] args) {
		BeanBox.getBean(A.class);
	}
}
