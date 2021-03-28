package io.lemonlabs.uri

import cats.effect._
import org.http4s.Method.GET
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import cats.effect.Blocker
import org.http4s.client.{Client, JavaNetClientBuilder}

import java.util.concurrent._

class Http {

  import scala.concurrent.ExecutionContext.global
  private implicit val cs: ContextShift[IO] = IO.contextShift(global)
  private implicit val timer: Timer[IO] = IO.timer(global)

  // Server
  private val app = HttpRoutes.of[IO] {
    case GET -> Root / "hello" =>
      Ok("Hello")
  }.orNotFound
  private val server = BlazeServerBuilder[IO](global).bindHttp(8080, "localhost").withHttpApp(app).resource
  private val fiber = server.use(_ => IO.never).start.unsafeRunSync()
  def shutdownServer(): Unit = fiber.cancel.unsafeRunSync()

  // Client
  private val blockingPool = Executors.newFixedThreadPool(5)
  private val blocker = Blocker.liftExecutorService(blockingPool)
  val client: Client[IO] = JavaNetClientBuilder[IO](blocker).create
}
