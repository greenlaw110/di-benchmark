package com.greenlaw110.di_benchmark;

import org.osgl.$;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class E {

    @Override
    public int hashCode() {
        return $.hc("e");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof E);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
