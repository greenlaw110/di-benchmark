package com.greenlaw110.di_benchmark.configs;

import com.greenlaw110.di_benchmark.objects.A;
import com.greenlaw110.di_benchmark.objects.B;
import com.greenlaw110.di_benchmark.objects.C;
import com.greenlaw110.di_benchmark.objects.D1;
import com.greenlaw110.di_benchmark.objects.D2;
import com.greenlaw110.di_benchmark.objects.E;

import net.sf.jbeanbox.BeanBox;

public class JBeanBoxConfig2 {

	public static class ABox extends BeanBox {

		public A create() {
			return new A(new BBox().getBean());
		}

	}

	public static class BBox extends BeanBox {
		public B create() {
			return new B(new CBox().getBean());
		}
	}

	public static class CBox extends BeanBox {
		public C create() {
			return new C(new D1Box().getBean(), new D2Box().getBean());
		}
	}

	public static class D1Box extends BeanBox {
		public D1 create() {
			return new D1(new EBox().getBean());
		}
	}

	public static class D2Box extends BeanBox {
		public D2 create() {
			return new D2(new EBox().getBean());
		}
	}

	public static class EBox extends BeanBox {
		public E create() {
			return new E();
		}
	}

}
