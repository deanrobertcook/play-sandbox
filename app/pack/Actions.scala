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
import play.api.mvc.ActionTransformer
import play.api.mvc.AnyContent
import play.api.mvc.WrappedRequest

class LoggingAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
extends ActionBuilderImpl(parser) with Logging {

    override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
        logger.info("Calling action")
        block(request)
    }
}

class UserRequest[A](val username: Option[String], request: Request[A]) extends WrappedRequest[A](request)
class UserAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
extends ActionBuilderImpl(parser) with ActionTransformer[Request, UserRequest] {

    override protected def transform[A](request: Request[A]): Future[UserRequest[A]] = 
        Future.successful(new UserRequest(request.session.get("username"), request))


}

