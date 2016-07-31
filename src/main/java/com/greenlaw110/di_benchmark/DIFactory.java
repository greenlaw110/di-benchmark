package com.greenlaw110.di_benchmark;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.greenlaw110.di_benchmark.beans.*;
import dagger.Module;
import dagger.ObjectGraph;
import org.codejargon.feather.Feather;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.osgl.inject.Genie;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DIFactory {

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

    public static ServiceLocator hk2() {
        return ServiceLocatorUtilities.createAndPopulateServiceLocator();
    }

    public static Genie genie() {
        return Genie.createWithoutPlugins();
    }

    public static ApplicationContext spring() {
        return new AnnotationConfigApplicationContext("com.greenlaw110.di_benchmark");
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

}
