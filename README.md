# Dependency Injector Benchmark Set

This project benchmark the following DI solutions:

* [Vanilla](https://github.com/greenlaw110/di-benchmark/blob/master/src/main/java/com/greenlaw110/di_benchmark/DIFactory.java#L24) 
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

## Benchmarks

### Startup and first time fetch benchmark

```text
Split Starting up DI containers & instantiating a dependency graph 4999 times:
---------------------------------------------------------------------------------------
Spring scan: disabled
 Vanilla| start:     4ms   fetch:     5ms
   Guice| start:   619ms   fetch:  1067ms
 Feather| start:     7ms   fetch:   105ms
  Dagger| start:    80ms   fetch:   160ms
    Pico| start:   204ms   fetch:   267ms
   Genie| start:   385ms   fetch:   168ms
  Spring| start: 20827ms   fetch:  1681ms
```

### Runtime benchmark

```text
Runtime benchmark, fetch bean for 499999 times:
--------------------------------------------------
 Vanilla|    47ms
   Guice|   530ms
 Feather|   256ms
  Dagger|   159ms
   Genie|   153ms
    Pico|  2055ms
  Spring| 23294ms
```

## How to run the benchmark

You need [maven](http://maven.apache.org/) to run the benchmark program.

The project defined two profiles: `runtime` and `split_startup`.

```bash
#Run the `runtime` profile:
mvn clean compile exec:exec -Pruntime
```

This will run the Runtime benchmark, which fetch the bean of class `A` (as shown above) for `50K` times

```bash
#To run the `split_startup` profile:
mvn clean compile exec:exec -Psplit_startup
```

This will run the startup benchmark, which initialize the injector and load bean of class `A` for `5K` times.

To tune the warm up and iterations:

```
#For startup benchmark:
mvn clean compile exec:exec -Psplit_startup -Dstartup.iteration=100 -Dstartup.warmup=10
```

```
#For runtime benchmark:
mvn clean compile exec:exec -Pruntime -Druntime.iteration=1000 -Druntime.warmup=100
```

## Disclaim

The benchmark source code is originated from https://github.com/zsoltherpai/feather/tree/master/performance-test and adapted by [Gelin Luo](https://github.com/greenlaw110)
