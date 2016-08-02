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
Spring scan: enabled
   Guice|  1921ms
 Feather|   120ms
  Dagger|   269ms
    Pico|   743ms
   Genie|   416ms
  Spring| 43535ms
```

**Note**

1. An [amendment](#a1) has been implemented to split the container startup time and the first bean fetching time
2. Spring startup performance can be improved by disable package scanning. See [below](#a2) for more details

## Runtime benchmark

Benchmark runtime performance: fetching bean for 500K times, with 1K times warm up:

```text
Runtime benchmark, fetch bean for 499999 times:
--------------------------------------------------
   Guice|   719ms
 Feather|   284ms
  Dagger|   146ms
   Genie|   177ms
    Pico|  2057ms
  Spring| 22024ms
```

## How to run the benchmark

You need [maven](http://maven.apache.org/) to run the benchmark program.

The project defined two profiles: `runtime` and `startup`.

```bash
#Run the `runtime` profile:
mvn clean compile exec:exec -Pruntime
```

This will run the Runtime benchmark, which fetch the bean of class `A` (as shown above) for `50K` times

```bash
#To run the `startup` profile:
mvn clean compile exec:exec -Pstartup
```

This will run the startup benchmark, which initialize the injector and load bean of class `A` for `5K` times.

To tune the warm up and iterations:

```
#For startup benchmark:
mvn clean compile exec:exec -Pstartup -Dstartup.iteration=100 -Dstartup.warmup=10
```

```
#For runtime benchmark:
mvn clean compile exec:exec -Pruntime -Druntime.iteration=1000 -Druntime.warmup=100
```

## Amendment 

### <a id="a1">Split container startup and first bean load benchmark</a>

As per [zapov's comment](https://www.reddit.com/r/java/comments/4vfw57/a_simple_program_benchmark_dependency_injection/d5y6hbz), another profile has been added into the project: `split_startup`, which reports the startup and fetch the first component performance respectively, and the new benchmark shows:

```
Split Starting up DI containers & instantiating a dependency graph 4999 times:
---------------------------------------------------------------------------------------
Spring scan: enabled
   Guice| start:   831ms   fetch:  1387ms
 Feather| start:    10ms   fetch:   147ms
  Dagger| start:    96ms   fetch:   238ms
    Pico| start:   307ms   fetch:   356ms
   Genie| start:   348ms   fetch:   234ms
  Spring| start: 40671ms   fetch:  2024ms
```

Here is the command the run split startup benchmark:

`mvn clean compile exec:exec -Psplit_startup`

### <a id="a2">Add new benchmark for Spring startup without package scanning</a>

As per [meotau's comment](https://www.reddit.com/r/java/comments/4vfw57/a_simple_program_benchmark_dependency_injection/d5yasgl), I have added a property `springscan` to allow toggle off package scanning for Spring. The startup and startup split result without package scanning are:

```
Starting up DI containers & instantiating a dependency graph 4999 times:
---------------------------------------------------------------------------------------
Spring scan: disabled
   Guice|  2071ms
 Feather|   144ms
  Dagger|   296ms
    Pico|   788ms
   Genie|   616ms
  Spring| 24286ms
```

```
Split Starting up DI containers & instantiating a dependency graph 4999 times:
---------------------------------------------------------------------------------------
Spring scan: disabled
   Guice| start:   929ms   fetch:  1655ms
 Feather| start:    15ms   fetch:   173ms
  Dagger| start:   133ms   fetch:   333ms
    Pico| start:   385ms   fetch:   477ms
   Genie| start:   594ms   fetch:   333ms
  Spring| start: 26284ms   fetch:  2156ms
```

The result shows we can roughly say Spring's container startup speed nearly doubled without package scanning but it is still a way slower than other products. Even though you don't get the performance gain by free, you have to manually construct your object graph and that immediately decrease the usability of Spring.

## Disclaim

The benchmark source code is originated from https://github.com/zsoltherpai/feather/tree/master/performance-test and adapted by [Gelin Luo](https://github.com/greenlaw110)
