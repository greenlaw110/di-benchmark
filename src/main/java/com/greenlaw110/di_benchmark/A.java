package com.greenlaw110.di_benchmark;

import org.osgl.$;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@Scope("prototype")
public class A {
    private final B b;

    @Inject
    public A(B b) {
        this.b = b;
    }

    @Override
    public int hashCode() {
        return $.hc("a", b);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof A && $.eq(((A) obj).b, b));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
