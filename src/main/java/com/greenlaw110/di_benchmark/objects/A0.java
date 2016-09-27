package com.greenlaw110.di_benchmark.objects;

import com.github.drinkjava2.InjectBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@InjectBox
@Component
@Singleton
public class A0 {
	private final B b;

	@InjectBox
	@Inject
	public A0(B b) {
		this.b = b;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		A0 a = (A0) o;
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
