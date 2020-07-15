package com.greenlaw110.di_benchmark;

import static com.greenlaw110.di_benchmark.DIFactory.dagger;
import static com.greenlaw110.di_benchmark.DIFactory.genie;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxAnnotation;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxNormal;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxTypeSafe;
import static com.greenlaw110.di_benchmark.DIFactory.pico;
import static com.greenlaw110.di_benchmark.DIFactory.spring;

import org.codejargon.feather.Feather;
import org.osgl.inject.Genie;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.ApplicationContext;

import com.github.drinkjava2.jbeanbox.BeanBoxContext;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.greenlaw110.di_benchmark.DIFactory.VanillaContainer;
import com.greenlaw110.di_benchmark.objects.A;

import dagger.ObjectGraph;

/**
 * Measures bootstrap cost of different DI tools. An iteration includes creating
 * an injector and instantiating the dependency graph.
 */
public class SplitStartupBenchmark {

	public void run(final int iterations) {
		benchmarkExplanation(iterations);

		StopWatch.startAndFetch("Vanilla", (start, fetch) -> {
			for (int i = 0; i < iterations; ++i) {
				long ms = System.currentTimeMillis();
				VanillaContainer injector = new VanillaContainer();
				long ms2 = System.currentTimeMillis();
				start.addAndGet(ms2 - ms);
				injector.getInstance(A.class);
				fetch.addAndGet(System.currentTimeMillis() - ms2);
			}
		});
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
		StopWatch.startAndFetch("jBeanBoxNormal", (start, fetch) -> {
			for (int i = 0; i < iterations; ++i) {
				long ms = System.currentTimeMillis();
				BeanBoxContext jbeanboxNormal = jbeanboxNormal();
				long ms2 = System.currentTimeMillis();
				start.addAndGet(ms2 - ms);
				jbeanboxNormal.getBean(A.class);
				fetch.addAndGet(System.currentTimeMillis() - ms2);
			}
		});
		StopWatch.startAndFetch("jBeanBoxTypeSafe", (start, fetch) -> {
			for (int i = 0; i < iterations; ++i) {
				long ms = System.currentTimeMillis();
				BeanBoxContext jbeanboxTypeSafe = jbeanboxTypeSafe();
				long ms2 = System.currentTimeMillis();
				start.addAndGet(ms2 - ms);
				jbeanboxTypeSafe.getBean(A.class);
				fetch.addAndGet(System.currentTimeMillis() - ms2);
			}
		});
		StopWatch.startAndFetch("jBeanBoxAnnotation", (start, fetch) -> {
			for (int i = 0; i < iterations; ++i) {
				long ms = System.currentTimeMillis();
				BeanBoxContext jbeanboxAnnotation = jbeanboxAnnotation();
				long ms2 = System.currentTimeMillis();
				start.addAndGet(ms2 - ms);
				jbeanboxAnnotation.getBean(A.class);
				fetch.addAndGet(System.currentTimeMillis() - ms2);
			}
		});

		if (iterations < 500000) {
			StopWatch.startAndFetch("SpringJavaConfiguration", (start, fetch) -> {
				for (int i = 0; i < iterations; ++i) {
					long ms = System.currentTimeMillis();
					ApplicationContext applicationContext = spring(false);
					long ms2 = System.currentTimeMillis();
					start.addAndGet(ms2 - ms);
					applicationContext.getBean(A.class);
					fetch.addAndGet(System.currentTimeMillis() - ms2);
				}
			});
		}
		if (iterations < 500000) {
			StopWatch.startAndFetch("SpringAnnotationScanned", (start, fetch) -> {
				for (int i = 0; i < iterations; ++i) {
					long ms = System.currentTimeMillis();
					ApplicationContext applicationContext = spring(true);
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
				String.format("Split Starting up DI containers & instantiating a dependency graph %s times:\n%s",
						iterations, "-------------------------------------------------------------------------------"));
	}

}
