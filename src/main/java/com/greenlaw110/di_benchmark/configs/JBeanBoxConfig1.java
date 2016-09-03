package com.greenlaw110.di_benchmark.configs;

import com.greenlaw110.di_benchmark.objects.A;
import com.greenlaw110.di_benchmark.objects.B;
import com.greenlaw110.di_benchmark.objects.C;
import com.greenlaw110.di_benchmark.objects.D1;
import com.greenlaw110.di_benchmark.objects.D2;
import com.greenlaw110.di_benchmark.objects.E;

import net.sf.jbeanbox.BeanBox;

public class JBeanBoxConfig1 {
	public static class ABox extends BeanBox {
		{
			this.setConstructor(A.class, B.class);
		}
	}

	public static class BBox extends BeanBox {
		{
			this.setConstructor(B.class, C.class);
		}
	}

	public static class CBox extends BeanBox {
		{
			this.setConstructor(C.class, D1.class, D2.class);
		}
	}

	public static class D1Box extends BeanBox {
		{
			this.setConstructor(D1.class, E.class);
		}
	}

	public static class D2Box extends BeanBox {
		{
			this.setConstructor(D2.class, E.class);
		}
	}
}
