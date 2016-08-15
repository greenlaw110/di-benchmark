package com.greenlaw110.di_benchmark.objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Objects;

@Component
@Scope("prototype")
public class D1 {

    @Inject
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
