package io.lemonlabs.mtfh

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import java.util.UUID
import java.util.concurrent.TimeUnit
import scala.util.Random

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 50, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 50, time = 200, timeUnit = TimeUnit.MILLISECONDS)
class MachineTimeForHumansBenchmark {

  var a: Int = _
  var b: Int = _

  var key: UUID = _
  var oneHundredInts: Map[UUID, Int] = _
  var oneHundred10MegaByteBlobs: Map[UUID, Array[Byte]] = _

  var http: Http = _

  val tenMegaBytesSize = 1048576
  
  @Setup def setUp(): Unit = {
    
    def randomInt() = Random.between(Int.MinValue, Int.MaxValue)

    a = randomInt()
    b = randomInt()

    oneHundredInts = List.fill(100)(UUID.randomUUID() -> randomInt()).toMap
    key = oneHundredInts.head._1

    oneHundred10MegaByteBlobs = oneHundredInts.map { case (key, _) => (key, Random.nextBytes(tenMegaBytesSize)) }

    http = new Http
    
//    println("a = " + a)
//    println("b = " + b)
//    println("oneHundredDoubles = " + oneHundredDoubles)
//    println("key = " + key)
  }

  @TearDown def teardown(): Unit = {
    http.shutdownServer()
  }
  
  @Benchmark
  @Fork(1)
  def aPlusB(bh: Blackhole): Unit =
    bh.consume(a + b)

  @Benchmark
  @Fork(1)
  def mapGet(bh: Blackhole): Unit =
    bh.consume(oneHundredInts.get(key))

  @Benchmark
  @Fork(1)
  def mapGet10MB(bh: Blackhole): Unit =
    bh.consume(oneHundred10MegaByteBlobs.get(key))

  @Benchmark
  @Fork(1)
  def halfThenSum(bh: Blackhole): Unit =
    bh.consume(oneHundredInts.values.map(_ / 2).sum)

  @Benchmark
  @Fork(1)
  def sumThenHalf(bh: Blackhole): Unit =
    bh.consume(oneHundredInts.values.sum / 2)

  @Benchmark
  @Fork(1)
  def createException(bh: Blackhole): Unit =
    bh.consume(new Exception("Oh no!").getStackTrace)

  @Benchmark
  @Fork(1)
  def parseJson(bh: Blackhole): Unit =
    bh.consume(
      io.circe.parser.parse (
        """
          {
            "foo": "bar",
            "baz": 123,
            "list of stuff": [ 4, 5, 6 ]
          }
        """
      )
    )

  @Benchmark
  @Fork(1)
  def httpLocalhost(bh: Blackhole): Unit =
    bh.consume(http.client.expect[String]("http://localhost:8080/hello").unsafeRunSync())

  @Benchmark
  @Fork(1)
  def httpLondon(bh: Blackhole): Unit =
    bh.consume(http.client.expect[String]("https://dynamodb.eu-west-2.amazonaws.com").unsafeRunSync())

  @Benchmark
  @Fork(1)
  def httpUsEastCoast(bh: Blackhole): Unit =
    bh.consume(http.client.expect [String]("https://dynamodb.us-east-1.amazonaws.com").unsafeRunSync())

  @Benchmark
  @Fork(1)
  def httpUsWestCoast(bh: Blackhole): Unit =
    bh.consume(http.client.expect[String]("https://dynamodb.us-west-1.amazonaws.com").unsafeRunSync())
}
