# Dependency Injector Benchmark Set

This project benchmark the following DI solutions:

* [Vanilla](https://github.com/greenlaw110/di-benchmark/blob/master/src/main/java/com/greenlaw110/di_benchmark/DIFactory.java#L24) 
* [Guice](https://github.com/google/guice) - 4.1.0
* [Genie](https://github.com/osglworks/java-di) - 0.2.0-SNAPSHOT
* [Feather](https://github.com/zsoltherpai/feather) - 1.0
* [Dagger](https://github.com/square/dagger) - 1.2.5
* [Pico](http://picocontainer.com/) - 2.15
* [jBeanBox](https://github.com/drinkjava2/jBeanBox) - 2.4-SNAPSHOT
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

## Benchmarks (on i3 M370 CPU)

### Startup and first time fetch benchmark

```text 
Split Starting up DI containers & instantiating a dependency graph 4999 times:
---------------------------------------------------------------------------------------
Spring scan: disabled
             Vanilla| start:     3ms   fetch:    15ms
               Guice| start:  1776ms   fetch:  3558ms
             Feather| start:    19ms   fetch:   251ms
              Dagger| start:   202ms   fetch:   377ms
                Pico| start:   710ms   fetch:   727ms
               Genie| start:  1675ms   fetch:   726ms
      jBeanBoxNormal| start:    15ms   fetch:  1523ms
    jBeanBoxTypeSafe| start:     8ms   fetch:   184ms
  jBeanBoxAnnotation| start:     7ms   fetch:    85ms
              Spring| start:110703ms   fetch:  9282ms 
```

### Runtime benchmark

```text
Runtime benchmark, fetch bean for 499999 times: 
--------------------------------------------------
             Vanilla|    71ms
               Guice|  2708ms
             Feather|  1727ms
              Dagger|   834ms
               Genie|  1587ms
                Pico| 11479ms
      jBeanBoxNormal| 14788ms
    jBeanBoxTypeSafe| 10031ms
  jBeanBoxAnnotation|  7441ms
              Spring|219018ms
```

## How to run the benchmark

You need JDK8 and [maven](http://maven.apache.org/) to run the benchmark program.

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
