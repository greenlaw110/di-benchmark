package com.greenlaw110.di_benchmark.beans;

import org.osgl.$;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@Scope("prototype")
public class D2 {
    private final E e;

    @Inject
    public D2(E e) {
        this.e = e;
    }

    @Override
    public int hashCode() {
        return $.hc("d2", e);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof D2 && $.eq(((D2) obj).e, e));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
