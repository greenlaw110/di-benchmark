package com.greenlaw110.di_benchmark.objects;

import com.github.drinkjava2.InjectBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@InjectBox  // for jBeanbox usage
@Component // for Spring usage
@Singleton // for JSR330 compliant DI library usage
public class A0 {
	private final B b;

	@InjectBox // for jBeanBox
	@Inject // for JSR330 compliant DI libraries
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
