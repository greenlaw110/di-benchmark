package com.greenlaw110.di_benchmark.objects;

import java.util.Objects;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.InjectBox;

@InjectBox(prototype = true)
@Component
@Scope("prototype")
public class A {
	private final B b;

	@InjectBox
	@Inject
	public A(B b) {
		this.b = b;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		A a = (A) o;
		return Objects.equals(b, a.b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(b);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
