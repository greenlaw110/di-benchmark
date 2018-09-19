package com.greenlaw110.di_benchmark.configs;

import static com.github.drinkjava2.jbeanbox.JBEANBOX.inject;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jbeanbox.BeanBoxContext;
import com.greenlaw110.di_benchmark.objects.A;
import com.greenlaw110.di_benchmark.objects.B;
import com.greenlaw110.di_benchmark.objects.C;
import com.greenlaw110.di_benchmark.objects.D1;
import com.greenlaw110.di_benchmark.objects.D2;
import com.greenlaw110.di_benchmark.objects.E;

public class JBeanBoxConfig1 {
	public static class PrototypeBox extends BeanBox {
		{
			setSingleton(false);
		}
	}

	public static class ABox extends PrototypeBox {
		{
			injectConstruct(A.class, B.class, inject(BBox.class));
		}
	}

	public static class ABox0 extends BeanBox { // Singleton
		{
			injectConstruct(A.class, B.class, inject(BBox.class));
		}
	}

	public static class BBox extends PrototypeBox {
		{
			injectConstruct(B.class, C.class, inject(CBox.class));
		}
	}

	public static class CBox extends PrototypeBox {
		{
			injectConstruct(C.class, D1.class, D2.class, inject(D1Box.class), inject(D2Box.class));
		}
	}

	public static class D1Box extends PrototypeBox {
		{
			injectConstruct(D1.class, E.class, inject(EBox.class));
		}
	}

	public static class D2Box extends PrototypeBox {
		{
			injectConstruct(D2.class, E.class, inject(EBox.class));
		}
	}

	public static class EBox extends PrototypeBox {
		{
			injectConstruct(E.class);
		}
	}

	public static void main(String[] args) {
		BeanBoxContext ctx = new BeanBoxContext();
		ctx.setAllowAnnotation(false);
		A a = ctx.getBean(ABox.class);
		System.out.println(a);
	}
}
