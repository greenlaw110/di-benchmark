package com.greenlaw110.di_benchmark.beans;

import org.osgl.$;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@Scope("prototype")
public class B {
    private final C c;

    @Inject
    public B(C c) {
        this.c = c;
    }

    @Override
    public int hashCode() {
        return $.hc("b", c);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof B && $.eq(((B) obj).c, c));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
