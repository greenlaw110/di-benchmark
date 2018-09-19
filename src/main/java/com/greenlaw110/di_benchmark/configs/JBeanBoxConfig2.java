package com.greenlaw110.di_benchmark.configs;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jbeanbox.BeanBoxContext;
import com.github.drinkjava2.jbeanbox.Caller;
import com.greenlaw110.di_benchmark.objects.A;
import com.greenlaw110.di_benchmark.objects.A0;
import com.greenlaw110.di_benchmark.objects.B;
import com.greenlaw110.di_benchmark.objects.C;
import com.greenlaw110.di_benchmark.objects.D1;
import com.greenlaw110.di_benchmark.objects.D2;
import com.greenlaw110.di_benchmark.objects.E;

public class JBeanBoxConfig2 {
	public static class PrototypeBox extends BeanBox {
		{
			this.setSingleton(false);
		}
	}

	public static class ABox extends PrototypeBox {
		Object create(Caller caller) {
			return new A((B) caller.getBean(BBox.class));
		}
	}

	public static class ABox0 extends BeanBox {// Singleton
		Object create(Caller caller) {
			return new A0(caller.getBean(BBox.class));
		}
	}

	public static class BBox extends PrototypeBox {
		Object create(Caller caller) {
			return new B((C) caller.getBean(CBox.class));
		}
	}

	public static class CBox extends PrototypeBox {
		Object create(Caller caller) {
			return new C((D1) caller.getBean(D1Box.class), (D2) caller.getBean(D2Box.class));
		}
	}

	public static class D1Box extends PrototypeBox {
		Object create(Caller caller) {
			return new D1((E) caller.getBean(EBox.class));
		}
	}

	public static class D2Box extends PrototypeBox {
		Object create(Caller caller) {
			return new D2((E) caller.getBean(EBox.class));
		}
	}

	public static class EBox extends PrototypeBox {
		Object create() {
			return new E();
		}
	}

	public static void main(String[] args) {
		BeanBoxContext ctx = new BeanBoxContext();
		ctx.setAllowAnnotation(false);
		A a = ctx.getBean(ABox.class);
		System.out.println(a);
	}
}
