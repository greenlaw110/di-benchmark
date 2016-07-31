package com.greenlaw110.di_benchmark;

import java.util.concurrent.atomic.AtomicLong;

public class StopWatch {

    public static void millis(String description, Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println(String.format("%s\t %s ms", description, end -start));
    }

    interface StartAndFetch {
        void run(AtomicLong startup, AtomicLong fetch);
    }

    public static void startAndFetch(String description, StartAndFetch runnable) {
        AtomicLong startup = new AtomicLong(0);
        AtomicLong fetch = new AtomicLong(0);
        runnable.run(startup, fetch);
        System.out.println(String.format("%s\t start: %sms, fetch %sms", description, startup.get(), fetch.get()));
    }
}
