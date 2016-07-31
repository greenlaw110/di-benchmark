package com.greenlaw110.di_benchmark;

import com.google.inject.Injector;
import dagger.ObjectGraph;
import org.codejargon.feather.Feather;
import org.osgl.inject.Genie;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.ApplicationContext;

import static com.greenlaw110.di_benchmark.DIFactory.*;

/**
 * Measures bootstrap cost of different DI tools.
 * An iteration includes creating an injector and instantiating the dependency graph.
 */
public class RuntimeBenchmark implements Benchmark {

    public void run(final int warmup, final int iterations) {
        benchmarkExplanation(iterations);
        Injector guice = guice();
        Feather feather = Feather.with();
        ObjectGraph dagger = dagger();
        MutablePicoContainer pico = pico();
        Genie genie = genie();
        ApplicationContext spring = spring();
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
        StopWatch.millis("Pico", () -> {
            for (int i = 0; i < iterations; ++i) {
                pico.getComponent(A.class);
            }
        });
        if (iterations < 500 * 1000) {
            StopWatch.millis("Spring", () -> {
                for (int i = 0; i < iterations; ++i) {
                    spring.getBean(A.class);
                }
            });
        }
    }

    private void benchmarkExplanation(int iterations) {
        System.out.println(
                String.format(
                        "Runtime benchmark, fetch bean for %s times:\n%s",
                        iterations,
                        "--------------------------------------------------")
        );
    }


}
