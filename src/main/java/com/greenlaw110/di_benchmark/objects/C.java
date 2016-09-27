package com.greenlaw110.di_benchmark.objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.InjectBox;

import javax.inject.Inject;
import java.util.Objects;

@InjectBox(prototype = true) // for jBeanBox usage
@Component // for Spring usage
@Scope("prototype") // for Spring usage
public class C {
	private final D1 d1;
	private final D2 d2;

	@InjectBox // for jBeanBox
	@Inject // for JSR330 compliant DI libraries
	public C(D1 d1, D2 d2) {
		this.d1 = d1;
		this.d2 = d2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		C c = (C) o;
		return Objects.equals(d1, c.d1) && Objects.equals(d2, c.d2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(d1, d2);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
