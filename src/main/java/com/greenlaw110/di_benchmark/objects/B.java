package com.greenlaw110.di_benchmark.objects;

import java.util.Objects;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.InjectBox;

@InjectBox(prototype = true)
@Component
@Scope("prototype")
public class B {
	private final C c;

	@InjectBox
	@Inject
	public B(C c) {
		this.c = c;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		B b = (B) o;
		return Objects.equals(c, b.c);
	}

	@Override
	public int hashCode() {
		return Objects.hash(c);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
