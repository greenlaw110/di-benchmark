package com.greenlaw110.di_benchmark;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dagger.ObjectGraph;
import org.codejargon.feather.Feather;
import org.osgl.inject.Genie;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.ApplicationContext;

import static com.greenlaw110.di_benchmark.DIFactory.*;

/**
 Measures bootstrap cost of different DI tools.
 An iteration includes creating an injector and instantiating the dependency graph.
 */
public class SplitStartupBenchmark {

    public void run(final int iterations) {
        benchmarkExplanation(iterations);
        StopWatch.startAndFetch("Guice", (start, fetch) -> {
            for (int i = 0; i < iterations; ++i) {
                long ms = System.currentTimeMillis();
                Injector injector = Guice.createInjector();
                long ms2 = System.currentTimeMillis();
                start.addAndGet(ms2 - ms);
                injector.getInstance(A.class);
                fetch.addAndGet(System.currentTimeMillis() - ms2);
            }
        });
        StopWatch.startAndFetch("Feather", (start, fetch) -> {
            for (int i = 0; i < iterations; ++i) {
                long ms = System.currentTimeMillis();
                Feather feather = Feather.with();
                long ms2 = System.currentTimeMillis();
                start.addAndGet(ms2 - ms);
                feather.instance(A.class);
                fetch.addAndGet(System.currentTimeMillis() - ms2);
            }
        });
        StopWatch.startAndFetch("Dagger", (start, fetch) -> {
            for (int i = 0; i < iterations; ++i) {
                long ms = System.currentTimeMillis();
                ObjectGraph dagger = dagger();
                long ms2 = System.currentTimeMillis();
                start.addAndGet(ms2 - ms);
                dagger.get(A.class);
                fetch.addAndGet(System.currentTimeMillis() - ms2);
            }
        });
        StopWatch.startAndFetch("Pico", (start, fetch) -> {
            for (int i = 0; i < iterations; ++i) {
                long ms = System.currentTimeMillis();
                MutablePicoContainer pico = pico();
                long ms2 = System.currentTimeMillis();
                start.addAndGet(ms2 - ms);
                pico.getComponent(A.class);
                fetch.addAndGet(System.currentTimeMillis() - ms2);
            }
        });
        StopWatch.startAndFetch("Genie", (start, fetch) -> {
            for (int i = 0; i < iterations; ++i) {
                long ms = System.currentTimeMillis();
                Genie genie = genie();
                long ms2 = System.currentTimeMillis();
                start.addAndGet(ms2 - ms);
                genie.get(A.class);
                fetch.addAndGet(System.currentTimeMillis() - ms2);
            }
        });
        if (iterations < 5000) {
            StopWatch.startAndFetch("Spring", (start, fetch) -> {
                for (int i = 0; i < iterations; ++i) {
                    long ms = System.currentTimeMillis();
                    ApplicationContext applicationContext = spring();
                    long ms2 = System.currentTimeMillis();
                    start.addAndGet(ms2 - ms);
                    applicationContext.getBean(A.class);
                    fetch.addAndGet(System.currentTimeMillis() - ms2);
                }
            });
        }
    }

    private void benchmarkExplanation(int iterations) {
        System.out.println(
                String.format(
                        "Split Starting up DI containers & instantiating a dependency graph %s times:\n%s",
                        iterations,
                        "---------------------------------------------------------------------------------------")
        );
    }


}
