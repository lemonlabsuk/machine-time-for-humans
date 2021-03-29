# Machine Time For Humans Slide Deck

[View the slide deck](https://lemonlabs.io/seven-year-method)

## Run the benchmarks

```shell
cd benchmarks
sbt "jmh:run"
```

## Results

```
[info] Benchmark                                      Mode  Cnt          Score        Error  Units
[info] MachineTimeForHumansBenchmark.aPlusB           avgt   50          2.186 ±      0.023  ns/op
[info] MachineTimeForHumansBenchmark.createException  avgt   50       6767.969 ±     80.360  ns/op
[info] MachineTimeForHumansBenchmark.halfThenSum      avgt   50       2246.976 ±    735.552  ns/op
[info] MachineTimeForHumansBenchmark.httpLocalhost    avgt   50     263894.653 ±  53143.199  ns/op
[info] MachineTimeForHumansBenchmark.httpLondon       avgt   50   15538277.589 ± 702743.456  ns/op
[info] MachineTimeForHumansBenchmark.httpUsEastCoast  avgt   50   91700505.913 ± 608224.662  ns/op
[info] MachineTimeForHumansBenchmark.httpUsWestCoast  avgt   50  155599630.960 ± 510334.860  ns/op
[info] MachineTimeForHumansBenchmark.mapGet           avgt   50          8.829 ±      0.219  ns/op
[info] MachineTimeForHumansBenchmark.mapGet10MB       avgt   50          8.669 ±      0.126  ns/op
[info] MachineTimeForHumansBenchmark.parseJson        avgt   50        548.874 ±     11.021  ns/op
[info] MachineTimeForHumansBenchmark.sumThenHalf      avgt   50        905.362 ±     10.541  ns/op
```
