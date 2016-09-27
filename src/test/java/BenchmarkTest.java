import static com.greenlaw110.di_benchmark.DIFactory.dagger;
import static com.greenlaw110.di_benchmark.DIFactory.feather;
import static com.greenlaw110.di_benchmark.DIFactory.genie;
import static com.greenlaw110.di_benchmark.DIFactory.guice;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxAnnotation;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxNormal;
import static com.greenlaw110.di_benchmark.DIFactory.jbeanboxTypeSafe;
import static com.greenlaw110.di_benchmark.DIFactory.pico;
import static com.greenlaw110.di_benchmark.DIFactory.spring;
import static com.greenlaw110.di_benchmark.DIFactory.vanilla;

import org.codejargon.feather.Feather;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.osgl.inject.Genie;
import org.picocontainer.PicoContainer;
import org.springframework.context.ApplicationContext;

import com.github.drinkjava2.BeanBoxContext;
import com.google.inject.Injector;
import com.greenlaw110.di_benchmark.DIFactory.VanillaContainer;
import com.greenlaw110.di_benchmark.objects.A;

import dagger.ObjectGraph;

public class BenchmarkTest extends Assert {

	static VanillaContainer vanilla;
	static Injector guice;
	static Feather feather;
	static ObjectGraph dagger;
	static PicoContainer pico;
	static Genie genie;
	static ApplicationContext spring;
	static BeanBoxContext jbeanboxNormal;
	static BeanBoxContext jbeanboxTypeSafe;
	static BeanBoxContext jbeanboxAnnotation;

	@BeforeClass
	public static void setup() {
		vanilla = vanilla();
		guice = guice();
		feather = feather();
		dagger = dagger();
		pico = pico();
		genie = genie();
		spring = spring(true);
		jbeanboxNormal = jbeanboxNormal();
		jbeanboxTypeSafe = jbeanboxTypeSafe();
		jbeanboxAnnotation = jbeanboxAnnotation();
	}

	@org.junit.Test
	public void equality() {
		A vanillaA = vanilla.getInstance(A.class);
		A guiceA = guice.getInstance(A.class);
		A featherA = feather.instance(A.class);
		A daggerA = dagger.get(A.class);
		A picoA = pico.getComponent(A.class);
		A genieA = genie.get(A.class);
		A springA = spring.getBean(A.class);
		A jbeanboxNormalA = jbeanboxNormal.getBean(A.class);
		A jbeanboxTypeSafeA = jbeanboxTypeSafe.getBean(A.class);
		A jbeanboxAnnotatioA = jbeanboxAnnotation.getBean(A.class);

		eq(vanillaA, guiceA);
		eq(guiceA, featherA);
		eq(featherA, daggerA);
		eq(daggerA, picoA);
		eq(picoA, genieA);
		eq(genieA, springA);
		eq(springA, jbeanboxNormalA);
		eq(jbeanboxNormalA, jbeanboxTypeSafeA);
		eq(jbeanboxTypeSafeA, jbeanboxAnnotatioA);
	}

	@org.junit.Test
	public void identity() {
		A vanillaA = vanilla.getInstance(A.class);
		A guiceA = guice.getInstance(A.class);
		A featherA = feather.instance(A.class);
		A daggerA = dagger.get(A.class);
		A picoA = pico.getComponent(A.class);
		A genieA = genie.get(A.class);
		A springA = spring.getBean(A.class);
		A jbeanboxNormalA = jbeanboxNormal.getBean(A.class);
		A jbeanboxTypeSafeA = jbeanboxTypeSafe.getBean(A.class);
		A jbeanboxAnnotatioA = jbeanboxAnnotation.getBean(A.class);

		A vanillaB = vanilla.getInstance(A.class);
		A guiceB = guice.getInstance(A.class);
		A featherB = feather.instance(A.class);
		A daggerB = dagger.get(A.class);
		A picoB = pico.getComponent(A.class);
		A genieB = genie.get(A.class);
		A springB = spring.getBean(A.class);
		A jbeanboxNormalB = jbeanboxNormal.getBean(A.class);
		A jbeanboxTypeSafeB = jbeanboxTypeSafe.getBean(A.class);
		A jbeanboxAnnotatioB = jbeanboxAnnotation.getBean(A.class);

		assertNotSame(vanillaA, vanillaB);
		assertNotSame(guiceA, guiceB);
		assertNotSame(featherA, featherB);
		assertNotSame(daggerA, daggerB);
		assertNotSame(picoA, picoB);
		assertNotSame(genieA, genieB);
		assertNotSame(springA, springB);
		assertNotSame(jbeanboxNormalA, jbeanboxNormalB);
		assertNotSame(jbeanboxTypeSafeA, jbeanboxTypeSafeB);
		assertNotSame(jbeanboxAnnotatioA, jbeanboxAnnotatioB);
	}

	private void eq(Object a, Object b) {
		assertEquals(a, b);
	}

}
