package com.greenlaw110.di_benchmark;

import static com.greenlaw110.di_benchmark.DIFactory.dagger;
import static com.greenlaw110.di_benchmark.DIFactory.genie;
import static com.greenlaw110.di_benchmark.DIFactory.guice;
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

import com.google.inject.Injector;
import com.greenlaw110.di_benchmark.DIFactory.VanillaContainer;
import com.greenlaw110.di_benchmark.objects.A;

import dagger.ObjectGraph;
import net.sf.jbeanbox.BeanBoxContext;

/**
 * Measures bootstrap cost of different DI tools. An iteration includes creating an injector and instantiating the
 * dependency graph.
 */
public class RuntimeBenchmark {

	public void run(final int warmup, final int iterations) {
		benchmarkExplanation(iterations);
		VanillaContainer vanilla = vanilla();
		Injector guice = guice();
		Feather feather = Feather.with();
		ObjectGraph dagger = dagger();
		MutablePicoContainer pico = pico();
		Genie genie = genie();
		BeanBoxContext jbeanboxNormal = jbeanboxNormal();
		BeanBoxContext jbeanboxTypeSafe = jbeanboxTypeSafe();
		BeanBoxContext jbeanboxAnnotation = jbeanboxAnnotation();

		ApplicationContext spring = spring(false);
		for (int i = 0; i < warmup; ++i) {
			feather.instance(A.class);
			genie.get(A.class);
			guice.getInstance(A.class);
			pico.getComponent(A.class);
			dagger.get(A.class);
			spring.getBean(A.class);
		}
		StopWatch.millis("Vanilla", () -> {
			for (int i = 0; i < iterations; ++i) {
				vanilla.getInstance(A.class);
			}
		});
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
		StopWatch.millis("jBeanBoxNormal", () -> {
			for (int i = 0; i < iterations; ++i) {
				jbeanboxNormal.getBean(A.class);
			}
		});
		StopWatch.millis("jBeanBoxTypeSafe", () -> {
			for (int i = 0; i < iterations; ++i) {
				jbeanboxTypeSafe.getBean(A.class);
			}
		});
		StopWatch.millis("jBeanBoxAnnotation", () -> {
			for (int i = 0; i < iterations; ++i) {
				jbeanboxAnnotation.getBean(A.class);
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
		System.out.println(String.format("Runtime benchmark, fetch bean for %s times:\n%s", iterations,
				"--------------------------------------------------"));
	}

}
