# Dependency Injector Benchmark Set

This project benchmark the following DI solutions:

* [Vanilla](https://github.com/greenlaw110/di-benchmark/blob/master/src/main/java/com/greenlaw110/di_benchmark/DIFactory.java#L24) 
* [Guice](https://github.com/google/guice) - 4.1.0
* [Genie](https://github.com/osglworks/java-di) - 0.2.0-SNAPSHOT
* [Feather](https://github.com/zsoltherpai/feather) - 1.0
* [Dagger](https://github.com/square/dagger) - 1.2.5
* [Pico](http://picocontainer.com/) - 2.15
* [jBeanBox](https://github.com/drinkjava2/jBeanBox) - 2.4.1
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
```
Split Starting up DI containers & instantiating a dependency graph 100 times (Prototype):
-------------------------------------------------------------------------------
                                      Vanilla| start:     1ms   fetch:     6ms
                                        Guice| start:   727ms   fetch:   747ms
                                      Feather| start:     6ms   fetch:    39ms
                                       Dagger| start:    74ms   fetch:    48ms
                                         Pico| start:   115ms   fetch:   127ms
                                        Genie| start:   658ms   fetch:    89ms
                               jBeanBoxNormal| start:     3ms   fetch:   123ms
                             jBeanBoxTypeSafe| start:     1ms   fetch:    40ms
                           jBeanBoxAnnotation| start:     1ms   fetch:   106ms
                      SpringJavaConfiguration| start:  4542ms   fetch:   621ms
                      SpringAnnotationScanned| start:  4668ms   fetch:   757ms
```

```
Runtime benchmark, fetch bean for 10000 times (Prototype):
--------------------------------------------------
                                      Vanilla|    11ms
                                        Guice|   153ms
                                      Feather|    59ms
                                       Dagger|    43ms
                                        Genie|    52ms
                                         Pico|   430ms
                               jBeanBoxNormal|  3791ms
                             jBeanBoxTypeSafe|   950ms
                           jBeanBoxAnnotation|  4603ms
                      SpringJavaConfiguration|  5003ms
                      SpringAnnotationScanned|  6331ms
```

If change configurations to Singleton (only compared jBeanBox & Spring):
```
Runtime benchmark, fetch bean for 100000 times:
--------------------------------------------------
                               jBeanBoxNormal|    47ms
                             jBeanBoxTypeSafe|    31ms
                           jBeanBoxAnnotation|    78ms
                      SpringJavaConfiguration|    94ms
                      SpringAnnotationScanned|    78ms
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
