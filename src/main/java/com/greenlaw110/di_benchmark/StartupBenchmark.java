package com.greenlaw110.di_benchmark;

import static com.greenlaw110.di_benchmark.DIFactory.dagger;
import static com.greenlaw110.di_benchmark.DIFactory.genie;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxAnnotation;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxNormal;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxTypeSafe;
import static com.greenlaw110.di_benchmark.DIFactory.pico;
import static com.greenlaw110.di_benchmark.DIFactory.spring;
import static com.greenlaw110.di_benchmark.DIFactory.vanilla;

import org.codejargon.feather.Feather;
import org.osgl.inject.Genie;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.ApplicationContext;

import com.github.drinkjava2.BeanBoxContext;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.greenlaw110.di_benchmark.DIFactory.DaggerComponent;
import com.greenlaw110.di_benchmark.DIFactory.VanillaContainer;
import com.greenlaw110.di_benchmark.objects.A;

/**
 * Measures bootstrap cost of different DI tools. An iteration includes creating
 * an injector and instantiating the dependency graph.
 */
public class StartupBenchmark {

	public void run(final int warmup, final int iterations) {
		benchmarkExplanation(iterations);
		for (int i = 0; i < warmup; ++i) {
			vanilla().getInstance(A.class);
			Feather.with().instance(A.class);
			genie().get(A.class);
			Guice.createInjector().getInstance(A.class);
			pico().getComponent(A.class);
			dagger().get(A.class);
			jbeanboxNormal().getBean(A.class);
			jbeanboxTypeSafe().getBean(A.class);
			jbeanboxAnnotation().getBean(A.class);
			spring(false).getBean(A.class);
			spring(true).getBean(A.class);
		}
		StopWatch.millis("Vanilla", () -> {
			for (int i = 0; i < iterations; ++i) {
				VanillaContainer vanilla = new VanillaContainer();
				vanilla.getInstance(A.class);
			}
		});
		StopWatch.millis("Guice", () -> {
			for (int i = 0; i < iterations; ++i) {
				Injector injector = Guice.createInjector();
				injector.getInstance(A.class);
			}
		});
		StopWatch.millis("Feather", () -> {
			for (int i = 0; i < iterations; ++i) {
				Feather feather = Feather.with();
				feather.instance(A.class);
			}
		});
		StopWatch.millis("Dagger", () -> {
			for (int i = 0; i < iterations; ++i) {
				DaggerComponent dagger = dagger();
				dagger.get(A.class);
			}
		});
		StopWatch.millis("Pico", () -> {
			for (int i = 0; i < iterations; ++i) {
				MutablePicoContainer pico = pico();
				pico.getComponent(A.class);
			}
		});
		StopWatch.millis("Genie", () -> {
			for (int i = 0; i < iterations; ++i) {
				Genie genie = genie();
				genie.get(A.class);
			}
		});
		StopWatch.millis("jBeanBoxAnnotation", () -> {
			for (int i = 0; i < iterations; ++i) {
				BeanBoxContext jbeanboxAnnotation = jbeanboxAnnotation();
				jbeanboxAnnotation.getBean(A.class);
			}
		});
		StopWatch.millis("jBeanBoxTypeSafe", () -> {
			for (int i = 0; i < iterations; ++i) {
				BeanBoxContext jbeanboxTypeSafe = jbeanboxTypeSafe();
				jbeanboxTypeSafe.getBean(A.class);
			}
		});
		StopWatch.millis("jBeanBoxNormal", () -> {
			for (int i = 0; i < iterations; ++i) {
				BeanBoxContext jbeanboxNormal = jbeanboxNormal();
				jbeanboxNormal.getBean(A.class);
			}
		});
		if (iterations < 500000) {
			StopWatch.millis("SpringJavaConfiguration", () -> {
				for (int i = 0; i < iterations; ++i) {
					ApplicationContext applicationContext = spring(false);
					applicationContext.getBean(A.class);
				}
			});
		}
		if (iterations < 500000) {
			StopWatch.millis("SpringAnnotationScanned", () -> {
				for (int i = 0; i < iterations; ++i) {
					ApplicationContext applicationContext = spring(true);
					applicationContext.getBean(A.class);
				}
			});
		}
	}

	private void benchmarkExplanation(int iterations) {
		System.out.println(String.format("Starting up DI containers & instantiating a dependency graph %s times:\n%s",
				iterations, "------------------------------------------------------------------------"));
	}

}
