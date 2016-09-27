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
Split Starting up DI containers & instantiating a dependency graph 4999 times:
-------------------------------------------------------------------------------
                     Vanilla| start:     3ms   fetch:     5ms
                       Guice| start:   458ms   fetch:   800ms
                     Feather| start:     8ms   fetch:    73ms
                      Dagger| start:    46ms   fetch:   130ms
                        Pico| start:   166ms   fetch:   161ms
                       Genie| start:   478ms   fetch:    98ms
              jBeanBoxNormal| start:     7ms   fetch:   339ms
            jBeanBoxTypeSafe| start:     3ms   fetch:   162ms
          jBeanBoxAnnotation| start:     4ms   fetch:   597ms
     SpringJavaConfiguration| start: 13956ms   fetch:  1149ms
     SpringAnnotationScanned| start: 22302ms   fetch:  2738ms
```

### Runtime bean injection benchmark

```
Runtime benchmark, fetch new bean for 50K times:
---------------------------------------------------------
                     Vanilla|     3ms
                       Guice|   188ms
                     Feather|    68ms
                      Dagger|    28ms
                       Genie|    45ms
                        Pico|   353ms
              jBeanBoxNormal|  1698ms
            jBeanBoxTypeSafe|   880ms
          jBeanBoxAnnotation|  2591ms
     SpringJavaConfiguration|  1936ms
     SpringAnnotationScanned|  2369ms
```

```
Runtime benchmark, fetch singleton bean for 5M times:
---------------------------------------------------------
                     Vanilla|     5ms
                       Guice|   559ms
                     Feather|   180ms
                      Dagger|   746ms
                       Genie|   118ms
                        Pico|   225ms
              jBeanBoxNormal|    59ms
            jBeanBoxTypeSafe|    59ms
          jBeanBoxAnnotation|   105ms
     SpringJavaConfiguration|   245ms
     SpringAnnotationScanned|   228ms
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

To benchmark singleton injection:

```
mvn clean compile exec:exec -Pruntime -Dsingleton=true
```

## Disclaim

The benchmark source code is originated from https://github.com/zsoltherpai/feather/tree/master/performance-test and adapted by [Gelin Luo](https://github.com/greenlaw110)
