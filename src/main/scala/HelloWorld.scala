package org.drc

import hcore.Weather

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.Random

object HelloWorld {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    val res = for {
      _ <- reportFromThread(1)
      _ <- reportFromThread(2)
      _ <- reportFromThread(3)
      _ <- reportFromThread(4)
      w <- Weather.weather
    } yield {
      println(s"The weather in New York is $w")
      Weather.http.close()
    }

    Await.result(res, 10.seconds)
  }

  def reportFromThread(i: Int) = Future {
    val millis = Random.nextInt(2000)
    Thread.sleep(millis)
    println(s"$i: Hello world from ${Thread.currentThread().getName} after $millis")
  }
}
