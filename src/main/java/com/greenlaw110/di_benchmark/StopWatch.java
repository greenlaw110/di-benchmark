package com.greenlaw110.di_benchmark;

public class StopWatch {
    public static void millis(String description, Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println(String.format("%s\t %s ms", description, end -start));
    }
}
