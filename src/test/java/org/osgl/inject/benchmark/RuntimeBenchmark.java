package org.osgl.inject.benchmark;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dagger.ObjectGraph;
import org.codejargon.feather.Feather;
import org.junit.Test;
import org.osgl.inject.Genie;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.ApplicationContext;

/**
 * Measures bootstrap cost of different DI tools.
 * An iteration includes creating an injector and instantiating the dependency graph.
 */
public class RuntimeBenchmark {
    private static final int warmup = 10000;
    private static final int iterations = 1000 * 1000;

    @Test
    public void run() {
        benchmarkExplanation();
        Injector guice = Guice.createInjector();
        Feather feather = Feather.with();
        ObjectGraph dagger = StartupBenchmark.dagger();
        MutablePicoContainer pico = StartupBenchmark.pico();
        Genie genie = Genie.create();
        ApplicationContext spring = StartupBenchmark.spring();
        for (int i = 0; i < warmup; ++i) {
            feather.instance(A.class);
            genie.get(A.class);
            guice.getInstance(A.class);
            pico.getComponent(A.class);
            dagger.get(A.class);
            spring.getBean(A.class);
        }
        StopWatch.millis("Guice", () -> {
            for (int i = 0; i < iterations; ++i) {
                guice.getInstance(A.class);
            }
        });
        StopWatch.millis("Feather", () -> {
            for (int i = 0; i < iterations; ++i) {
                feather.instance(A.class);
            }
        });
        StopWatch.millis("Dagger", () -> {
            for (int i = 0; i < iterations; ++i) {
                dagger.get(A.class);
            }
        });
        StopWatch.millis("Genie", () -> {
            for (int i = 0; i < iterations; ++i) {
                genie.get(A.class);
            }
        });
        StopWatch.millis("PicoContainer", () -> {
            for (int i = 0; i < iterations; ++i) {
                pico.getComponent(A.class);
            }
        });
        //if (iterations < 100 * 100) {
            StopWatch.millis("Spring", () -> {
                for (int i = 0; i < iterations; ++i) {
                    spring.getBean(A.class);
                }
            });
        //}
    }

    private void benchmarkExplanation() {
        System.out.println(
                String.format(
                        "Runtime benchmark, fetch bean for %s times:",
                        iterations)
        );
    }


}
