package v1.test

import com.google.inject.Inject
import play.api.libs.mailer.{Email, MailerClient}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import play.api.mvc.Cookie
import pack.LoggingAction
import pack.UserAction

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

    \/

    Ok(views.html.pages.index())
      .withCookies(Cookie("test", "binary-binoculars-batman"))
  }

}
