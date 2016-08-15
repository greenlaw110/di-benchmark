package com.greenlaw110.di_benchmark;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.greenlaw110.di_benchmark.objects.*;
import dagger.Module;
import dagger.ObjectGraph;
import org.codejargon.feather.Feather;
import org.osgl.inject.Genie;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DIFactory {

    public static class VanillaContainer {
        public <T> T getInstance(Class<T> instanceClass) {
            if (A.class.equals(instanceClass)) {
                return (T) getA();
            }
            return null;
        }

        private A getA() {
            E e = new E();
            D1 d1 = new D1(e);
            D2 d2 = new D2(e);
            C c = new C(d1, d2);
            B b = new B(c);
            return new A(b);
        }
    }

    public static VanillaContainer vanilla() {
        return new VanillaContainer();
    }

    public static Injector guice() {
        return Guice.createInjector();
    }

    public static Feather feather() {
        return Feather.with();
    }

    public static MutablePicoContainer pico() {
        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(A.class);
        pico.addComponent(B.class);
        pico.addComponent(C.class);
        pico.addComponent(D1.class);
        pico.addComponent(D2.class);
        pico.addComponent(E.class);
        return pico;
    }

    public static Genie genie() {
        return Genie.createWithoutPlugins();
    }

    public static ObjectGraph dagger() {
        return ObjectGraph.create(new DaggerModule());
    }

    @Module(injects = {A.class})
    public static class DaggerModule {
        @dagger.Provides
        E e() {
            return new E();
        }
    }


    public static ApplicationContext spring(boolean scan) {
        if (scan) {
            return new AnnotationConfigApplicationContext("com.greenlaw110.di_benchmark.objects");
        } else {
            return new AnnotationConfigApplicationContext(SpringConfig.class);
        }
    }

    @Configuration
    static class SpringConfig {
        @Bean
        @Scope("prototype")
        public A a() {
            return new A(b());
        }

        @Bean
        @Scope("prototype")
        public B b() {
            return new B(c());
        }

        @Bean
        @Scope("prototype")
        public C c() {
            return new C(d1(), d2());
        }

        @Bean
        @Scope("prototype")
        public D1 d1() {
            return new D1(e());
        }

        @Bean
        @Scope("prototype")
        public D2 d2() {
            return new D2(e());
        }

        @Bean
        @Scope("prototype")
        public E e() {
            return new E();
        }

    }
}
