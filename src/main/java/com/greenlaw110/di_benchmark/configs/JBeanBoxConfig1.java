package com.greenlaw110.di_benchmark.configs;

import com.github.drinkjava2.BeanBox;
import com.github.drinkjava2.BeanBoxContext;
import com.greenlaw110.di_benchmark.objects.A;
import com.greenlaw110.di_benchmark.objects.B;
import com.greenlaw110.di_benchmark.objects.C;
import com.greenlaw110.di_benchmark.objects.D1;
import com.greenlaw110.di_benchmark.objects.D2;
import com.greenlaw110.di_benchmark.objects.E;

public class JBeanBoxConfig1 {
	public static class ProtoTypeBox extends BeanBox {
		{this.setPrototype(true);}
	}

	public static class ABox extends ProtoTypeBox {
		{
			this.setConstructor(A.class, B.class);
		}
	}

	public static class BBox extends ProtoTypeBox {
		{ 
			this.setConstructor(B.class, C.class);
		}
	}

	public static class CBox extends ProtoTypeBox {
		{ 
			this.setConstructor(C.class, D1.class, D2.class);
		}
	}

	public static class D1Box extends ProtoTypeBox {
		{ 
			this.setConstructor(D1.class, E.class);
		}
	}

	public static class D2Box extends ProtoTypeBox {
		{ 
			this.setConstructor(D2.class, E.class);
		}
	}

	public static class EBox extends ProtoTypeBox {
		{ 
			this.setClassOrValue(E.class);
		}
	}

	public static void main(String[] args) {
		BeanBoxContext ctx = new BeanBoxContext(JBeanBoxConfig1.class).setIgnoreAnnotation(true);
		ctx.getBean(A.class);
	}
}
