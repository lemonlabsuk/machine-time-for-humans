name := "latency-for-humans-benchmarks"

organization  := "io.lemonlabs"

version       := "0.0.1"

scalaVersion  := "2.13.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

enablePlugins(JmhPlugin)

val circeVersion = "0.12.3"
val http4sVersion = "0.21.20"

libraryDependencies ++= Seq(
  "io.circe"       %% "circe-parser"        % circeVersion,
  "org.http4s"     %% "http4s-dsl"          % http4sVersion,
  "org.http4s"     %% "http4s-blaze-server" % http4sVersion,
  "org.http4s"     %% "http4s-blaze-client" % http4sVersion,
  "ch.qos.logback" %  "logback-classic"     % "1.2.3"
)

logBuffered := false

fork := true
