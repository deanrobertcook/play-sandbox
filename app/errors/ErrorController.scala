package errors

import com.google.inject.Inject
import io.sentry.Sentry
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

class ErrorController @Inject() (cc: ControllerComponents) extends AbstractController(cc) with Logging {

  def sentry() = Action {
    val work = asyncWork(1)
    logger.info("Info message 1")
    logger.info("Info message 2")
    logger.error("Something serious 1")

    Await.result(work, 3.seconds)
    Ok(s"Errors have been logged")
  }

  def asyncWork(in: Int): Future[Int] = {
    Future {
      logger.info(s"Context message $in")
      Thread.sleep(1000)
      logger.error(s"Something serious async work: $in")

      Thread.sleep(1000)
      3
    }
  }

  def forbidden() = Action {
    Forbidden
  }

  def exception() = Action {
    throw new Exception("Something internal happened")
    Ok("Shouldn't get here")
  }

  def internal() = Action {
    InternalServerError
  }

  def badRequest() = Action {
    BadRequest(Json.obj("Field" -> "Ya darn goofed"))
  }
}