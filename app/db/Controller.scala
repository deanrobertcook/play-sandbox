package db

import com.google.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import anorm._

class Controller @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def database() = Action {
    Ok(s"Test database call")
  }

}
