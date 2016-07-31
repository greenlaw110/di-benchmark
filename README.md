# Depenedency Injector Benchmark Set

This project benchmark the following DI solutions:

* [Guice](https://github.com/google/guice) - 4.1.0
* [Genie](https://github.com/osglworks/java-di) - 0.2.0-SNAPSHOT
* [Feather](https://github.com/zsoltherpai/feather) - 1.0
* [Dagger](https://github.com/square/dagger) - 1.2.5
* [Pico](http://picocontainer.com/) - 2.15
* [Spring](http://projects.spring.io/spring-framework/) - 4.3.2.RELEASE

## Object Graph

```
Object
└── A
    └── B
        └── C
            ├── D1
            └── D2
                └── E
```

All injection is done through Constructor injection

## Startup benchmark

Benchmark Starting up DI containers & instantiating a dependency graph performance for 2000 times, with 200 warm up times

```text
Starting up DI containers & instantiating a dependency graph 2000 times:
Guice 733 milliseconds.
Feather 52 milliseconds.
Dagger 115 milliseconds.
PicoContainer 263 milliseconds.
Genie 308 milliseconds.
Spring 18676 milliseconds.
```

## Runtime benchmark

Benchmark runtime performance: fetching bean for 1M times, with 10K times warm up:

```text
Runtime benchmark, fetch bean for 1000000 times:
Guice 971 milliseconds.
Feather 436 milliseconds.
Dagger 251 milliseconds.
Genie 258 milliseconds.
PicoContainer 3665 milliseconds.
Spring 51009 milliseconds.
```
