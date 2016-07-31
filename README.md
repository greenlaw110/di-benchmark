# Depenedency Injector Benchmark Set

This project benchmark the following DI solutions:

* [Guice](https://github.com/google/guice) - 4.1.0
* [Genie](https://github.com/osglworks/java-di) - 0.2.0-SNAPSHOT
* [Feather](https://github.com/zsoltherpai/feather) - 1.0
* [Dagger](https://github.com/square/dagger) - 1.2.5
* [Pico](http://picocontainer.com/) - 2.15
* [Spring](http://projects.spring.io/spring-framework/) - 4.3.2.RELEASE

## Object Graph

Below is the object graph of the class to be injected in the benchmark program

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

Benchmark Starting up DI containers & instantiating a dependency graph performance for 5K times, with 200 warm up times

```text
Starting up DI containers & instantiating a dependency graph 4999 times:
---------------------------------------------------------------------------------------
Guice	 1392 ms
Feather	 128 ms
Dagger	 213 ms
Pico	 622 ms
Genie	 681 ms
Spring	 35872 ms
```

## Runtime benchmark

Benchmark runtime performance: fetching bean for 500K times, with 1K times warm up:

```text
Runtime benchmark, fetch bean for 499999 times:
--------------------------------------------------
Guice	 559 ms
Feather	 241 ms
Dagger	 152 ms
Genie	 162 ms
Pico	 1946 ms
Spring	 25809 ms
```

## How to run the benchmark

You need [maven](http://maven.apache.org/) to run the benchmark program.

The project defined two profiles: `runtime` and `startup`.

Run the `runtime` profile:

`mvn clean compile exec:exec -Pruntime`

This will run the Runtime benchmark, which fetch the bean of class `A` (as shown above) for `50K` times

To run the `startup` profile:

`mvn clean compile exec:exec -Pstartup`

This will run the startup benchmark, which initialize the injector and load bean of class `A` for `5K` times.

To tune the warm up and iterations:

For startup benchmark:

`mvn clean compile exec:exec -Pstartup -Dstartup.iteration=100 -Dstartup.warmup=10`

For runtime benchmark:

`mvn clean compile exec:exec -Pruntime -Druntime.iteration=1000 -Druntime.warmup=100`

**Note** As per [zapov's advice](https://www.reddit.com/r/java/comments/4vfw57/a_simple_program_benchmark_dependency_injection/), another profile has been added into the project: `split_startup`, which reports the startup and fetch the first component performance respectively, and the new benchmark shows:

```
Split Starting up DI containers & instantiating a dependency graph 4999 times:
---------------------------------------------------------------------------------------
Guice	 start: 675ms, fetch 1173ms
Feather	 start: 10ms, fetch 126ms
Dagger	 start: 75ms, fetch 171ms
Pico	 start: 267ms, fetch 322ms
Genie	 start: 331ms, fetch 229ms
Spring	 start: 33947ms, fetch 5088ms
```

Here is the command the run split startup benchmark:

`mvn clean compile exec:exec -Psplit_startup`


## Disclaim

The benchmark source code is originated from https://github.com/zsoltherpai/feather/tree/master/performance-test 