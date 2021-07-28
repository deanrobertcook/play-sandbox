package db

import anorm.{Macro, RowParser, SqlStringInterpolation}
import com.google.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.db.Database

case class Employee(firstName: String,
                    lastName: String,
                    departmentNumber: String)

class Controller @Inject() (db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  val parser: RowParser[Employee] = Macro.parser[Employee]("first_name", "last_name", "dept_no")

  def dbCall() = Action {
    val res = db.withConnection { implicit c =>
      SQL"SELECT * FROM employees".as(parser.*)
    }.take(5)

    Ok(s"Test database call $res")
  }

  def departments() = Action {
    val res = db.withConnection { implicit c =>
      SQL"""
            SELECT first_name, last_name, dept_no FROM employees e
            JOIN dept_emp d ON d.emp_no = e.emp_no
            ORDER BY e.last_name LIMIT 10
         """.as(parser.*)
    }.take(5)

    Ok(s"Test database call $res")
  }

}
