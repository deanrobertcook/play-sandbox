package pack

import javax.inject.Inject
import play.api.mvc.BodyParser
import play.api.mvc.BodyParsers
import scala.concurrent.ExecutionContext
import play.api.mvc.ActionBuilderImpl
import play.api.Logging
import play.api.mvc.Result
import scala.concurrent.Future
import play.api.mvc.Request

class LoggingAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
extends ActionBuilderImpl(parser) with Logging {
    override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
        logger.info("Calling action")
        block(request)
    }
}