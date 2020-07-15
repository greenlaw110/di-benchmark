package com.greenlaw110.di_benchmark.objects;

import java.util.Objects;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component // for Spring usage
@Scope("prototype") // for Spring usage
public class D1 {

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

}
