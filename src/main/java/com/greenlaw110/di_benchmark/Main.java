package com.greenlaw110.di_benchmark;

import org.osgl.util.S;

public class Main {

    private static final int WARM_UP = 200;
    private static final int ITERATION = 20000;

    public static void main(String[] args) {
        int warmUp = WARM_UP;
        int iteration = ITERATION;
        String singletonStr = conf("singleton");
        boolean singleton = S.notBlank(singletonStr) && Boolean.parseBoolean(singletonStr);
        String s = conf("warmup");
        if (S.notBlank(s)) {
            warmUp = Integer.parseInt(s);
        }
        s = conf("iteration");
        if (S.notBlank(s)) {
            iteration = Integer.parseInt(s);
        }
        s = conf("type");
        if (S.eq(s, "runtime")) {
            new RuntimeBenchmark().run(warmUp, iteration, singleton);
        }
        if (S.eq(s, "startup")) {
            new StartupBenchmark().run(warmUp, iteration);
        }
        if (S.eq(s, "split_startup")) {
            new SplitStartupBenchmark().run(iteration);
        }
    }

    private static String conf(String key) {
        return System.getProperty(key);
    }

}
