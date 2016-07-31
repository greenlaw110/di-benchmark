package com.greenlaw110.di_benchmark;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Objects;

@Component
@Scope("prototype")
public class D2 {
    private final E e;

    @Inject
    public D2(E e) {
        this.e = e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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

}
