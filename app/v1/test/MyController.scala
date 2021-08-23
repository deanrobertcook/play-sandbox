package v1.test

import com.google.inject.Inject
import play.api.libs.mailer.{Email, MailerClient}
import play.api.mvc.{AbstractController, Action, ControllerComponents, Cookie, Result}
import pack.LoggingAction
import pack.UserAction
import scalaz.{-\/, \/, \/-}

import scala.util.Random

class MyController @Inject() (loggingAction: LoggingAction,
                              userAction: UserAction,
                              cc: ControllerComponents,
                              mailerClient: MailerClient) extends AbstractController(cc) {

  def index(i: String) = loggingAction.andThen(userAction) {
    Ok(s"You asked for $i")
  }

  def sendEmail() = Action {
    val body = views.html.emails.testEmail("Finn the Human")
    val email = Email(
      "Simple email",
      "Mister FROM <from@email.com>",
      Seq("Miss TO <to@email.com>"),
      bodyText = Some(body.body))

    mailerClient.send(email)
    Ok(body)
  }

  def session() = Action {
    Redirect("/main")
      .withSession("username" -> "test_user")
  }

  def main() = Action {
    Ok(views.html.pages.index())
      .withCookies(Cookie("test", "binary-binoculars-batman"))
  }

  def scalaz() = Action {
    val res = for {
      t <- someBooleanCompThatFails(0)
      _ <- if (t) -\/(ClientError(BadRequest)) else \/-(())
      a <- someComputation(0)
      b <- someComputation(a)
      c <- someComputation(b)
    } yield Ok(s"We have a response: $c")
    res.fold( {
      case ClientError(res) => res
      case err => Ok(err.message)
    }, identity)
  }

  def someComputation(input: Int): GenError \/ Int = {
    println(s"someComputation($input)")
    val r = Random.nextInt(100)
    if (r > 80) -\/(RandomError(input, r)) else \/-(input + 1)
  }

  def someBooleanCompThatFails(input: Int): GenError \/ Boolean = {
    println(s"someBooleanCompThatFails($input)")
    val r = Random.nextInt(100)
    val r2 = Random.nextInt(100)
    if (r > 80) -\/(RandomError(input, r)) else \/-(r2 > 50)
  }

}

trait GenError {
  def message: String
}

case class ClientError(res: Result) extends GenError {
  override def message: String = res.toString()
}

case class RandomError(input: Int, randomValue: Int) extends GenError {
  override def message: String = s"Failed on input: $input with random value: $randomValue"
}
