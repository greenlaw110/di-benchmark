package com.greenlaw110.di_benchmark;

import java.util.concurrent.atomic.AtomicLong;

public class StopWatch {

    public static void millis(String description, Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println(String.format("%30s|%6sms", description, end - start));
    }

    interface StartAndFetch {
        void run(AtomicLong startup, AtomicLong fetch);
    }

    public static void startAndFetch(String description, StartAndFetch runnable) {
        AtomicLong startup = new AtomicLong(0);
        AtomicLong fetch = new AtomicLong(0);
        runnable.run(startup, fetch);
        System.out.println(String.format("%30s| start:%6sms   fetch:%6sms", description, startup.get(), fetch.get()));
    }
}
