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

import com.github.drinkjava2.jbeanbox.BeanBoxContext;
import com.google.inject.Injector;
import com.greenlaw110.di_benchmark.DIFactory.VanillaContainer;
import com.greenlaw110.di_benchmark.configs.JBeanBoxConfig1.ABox;
import com.greenlaw110.di_benchmark.configs.JBeanBoxConfig1.ABox0;
import com.greenlaw110.di_benchmark.objects.A;
import com.greenlaw110.di_benchmark.objects.A0;

import dagger.ObjectGraph;

/**
 * Measures bootstrap cost of different DI tools. An iteration includes creating
 * an injector and instantiating the dependency graph.
 */
public class RuntimeBenchmark {

	private static Class A_CLS = A.class;
	private static Class A_SINGLETON_CLS = A0.class;

	public void run(final int warmup, final int iterations, final boolean singleton) {
		benchmarkExplanation(iterations, singleton);
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
		ApplicationContext springScan = spring(true);

		final Class<?> CLS = singleton ? A_SINGLETON_CLS : A_CLS;

		for (int i = 0; i < warmup; ++i) {
			feather.instance(CLS);
			genie.get(CLS);
			guice.getInstance(CLS);
			pico.getComponent(CLS);
			dagger.get(CLS);
			spring.getBean(CLS);
			if (!singleton) {
				jbeanboxNormal.getBean(CLS);
				jbeanboxTypeSafe.getBean(CLS);
				jbeanboxAnnotation.getBean(CLS);
			}
		}

		StopWatch.millis("Vanilla", () -> {
			for (int i = 0; i < iterations; ++i) {
				vanilla.getInstance(CLS);
			}
		});

		StopWatch.millis("Guice", () -> {
			for (int i = 0; i < iterations; ++i) {
				guice.getInstance(CLS);
			}
		});

		StopWatch.millis("Feather", () -> {
			for (int i = 0; i < iterations; ++i) {
				feather.instance(CLS);
			}
		});

		StopWatch.millis("Dagger", () -> {
			for (int i = 0; i < iterations; ++i) {
				dagger.get(CLS);
			}
		});

		StopWatch.millis("Genie", () -> {
			for (int i = 0; i < iterations; ++i) {
				genie.get(CLS);
			}
		});

		StopWatch.millis("Pico", () -> {
			for (int i = 0; i < iterations; ++i) {
				pico.getComponent(CLS);
			}
		});

		Class<?> configBox1 = singleton ? com.greenlaw110.di_benchmark.configs.JBeanBoxConfig1.ABox0.class
				: com.greenlaw110.di_benchmark.configs.JBeanBoxConfig1.ABox.class;
		StopWatch.millis("jBeanBoxNormal", () -> {
			for (int i = 0; i < iterations; ++i) {
				jbeanboxNormal.getBean(configBox1);
			}
		});

		Class<?> configBox2 = singleton ? com.greenlaw110.di_benchmark.configs.JBeanBoxConfig2.ABox0.class
				: com.greenlaw110.di_benchmark.configs.JBeanBoxConfig2.ABox.class;
		StopWatch.millis("jBeanBoxTypeSafe", () -> {
			for (int i = 0; i < iterations; ++i) {
				jbeanboxTypeSafe.getBean(configBox2);
			}
		});

		StopWatch.millis("jBeanBoxAnnotation", () -> {
			for (int i = 0; i < iterations; ++i) {
				jbeanboxAnnotation.getBean(CLS);
			}
		});

		if (singleton || iterations < 500000) {
			StopWatch.millis("SpringJavaConfiguration", () -> {
				for (int i = 0; i < iterations; ++i) {
					spring.getBean(CLS);
				}
			});
		}
		if (singleton || iterations < 500000) {
			StopWatch.millis("SpringAnnotationScanned", () -> {
				for (int i = 0; i < iterations; ++i) {
					springScan.getBean(CLS);
				}
			});
		}

	}

	private void benchmarkExplanation(int iterations, boolean singleton) {
		System.out.println(String.format("Runtime benchmark, fetch %s bean for %s times:\n%s",
				singleton ? "singleton" : "new", iterations,
				"---------------------------------------------------------"));
	}

}
