package errors

import com.google.inject.Inject
import io.sentry.{Sentry, SentryLevel}
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

class ErrorController @Inject() (cc: ControllerComponents) extends AbstractController(cc) with Logging {

  def sentry() = Action {
    logger.info("Here's an info message")
    logger.error("Something serious")
    Sentry.captureMessage("Test message", SentryLevel.ERROR)
    try throw new Exception("This is a thrown exception")
    catch {
      case e: Exception =>
        Sentry.captureException(e)
    }
    Ok(s"Errors have been logged")
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