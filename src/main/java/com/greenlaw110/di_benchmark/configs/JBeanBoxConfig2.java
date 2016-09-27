package com.greenlaw110.di_benchmark.configs;

import com.github.drinkjava2.BeanBox;
import com.github.drinkjava2.BeanBoxContext;
import com.greenlaw110.di_benchmark.objects.A;
import com.greenlaw110.di_benchmark.objects.A0;
import com.greenlaw110.di_benchmark.objects.B;
import com.greenlaw110.di_benchmark.objects.C;
import com.greenlaw110.di_benchmark.objects.D1;
import com.greenlaw110.di_benchmark.objects.D2;
import com.greenlaw110.di_benchmark.objects.E;

public class JBeanBoxConfig2 {

	public static class ProtoTypeBox extends BeanBox {
		{this.setPrototype(true);}
	}

	public static class ABox extends ProtoTypeBox { 
		public A create() {
			return new A(context.getBean(B.class));
		}
	}
	
	public static class A0Box extends BeanBox { 
		public A0 create() {
			return new A0(context.getBean(B.class));
		}
	}

	public static class BBox extends ProtoTypeBox { 
		public B create() {
			return new B(context.getBean(C.class));
		}
	}

	public static class CBox extends ProtoTypeBox { 
		public C create() {
			return new C(context.getBean(D1.class), context.getBean(D2.class));
		}
	}

	public static class D1Box extends ProtoTypeBox { 
		public D1 create() {
			return new D1(context.getBean(E.class));
		}
	}

	public static class D2Box extends ProtoTypeBox { 
		public D2 create() {
			return new D2(context.getBean(E.class));
		}
	}

	public static class EBox extends ProtoTypeBox { 
		public E create() {
			return new E();
		}
	}

	public static void main(String[] args) {
		BeanBoxContext ctx = new BeanBoxContext(JBeanBoxConfig2.class).setIgnoreAnnotation(true);
		ctx.getBean(A.class);
	}
}
