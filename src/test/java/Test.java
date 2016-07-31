import com.greenlaw110.di_benchmark.A;
import org.junit.Assert;

import static com.greenlaw110.di_benchmark.DIFactory.*;

public class Test extends Assert {

    @org.junit.Test
    public void equality() {
        A guiceA = guice().getInstance(A.class);
        A featherA = feather().instance(A.class);
        A daggerA = dagger().get(A.class);
        A picoA = pico().getComponent(A.class);
        A genieA = genie().get(A.class);
        A springA = spring().getBean(A.class);

        eq(guiceA, featherA);
        eq(featherA, daggerA);
        eq(daggerA, picoA);
        eq(picoA, genieA);
        eq(genieA, springA);
    }

    private void eq(Object a, Object b) {
        assertEquals(a, b);
    }

}
