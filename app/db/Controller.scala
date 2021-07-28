package db

import anorm.{Macro, RowParser, SqlStringInterpolation}
import com.google.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.db.Database

case class Employee(firstName: String,
                    lastName: String)

class Controller @Inject() (db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  val parser: RowParser[Employee] = Macro.parser[Employee]("first_name", "last_name")

  def dbCall() = Action {
    val res = db.withConnection { implicit c =>
      SQL"SELECT * FROM employees".as(parser.*)
    }

    Ok(s"Test database call $res")
  }

}
