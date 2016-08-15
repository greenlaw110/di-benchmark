package com.greenlaw110.di_benchmark.objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Objects;

@Component
@Scope("prototype")
public class A {
    private final B b;

    @Inject
    public A(B b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
