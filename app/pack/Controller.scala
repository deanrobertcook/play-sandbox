package pack

import anorm.{Macro, RowParser, SqlStringInterpolation}
import macrotest.IdentityMacro
import play.api.db.Database
import play.api.mvc.{AbstractController, ControllerComponents}
import anorm.SqlParser._

import javax.inject.Inject

case class Employee(firstName: String,
                    lastName: String,
                    departmentNumber: String)

class Controller @Inject() (db: Database, cc: ControllerComponents, tl: TestClass) extends AbstractController(cc) {

  @IdentityMacro
  val parser: RowParser[Employee] = Macro.parser[Employee]("first_name", "last_name", "dept_no")

  def dbCall() = Action {
    val res = db.withConnection { implicit c =>
      SQL"SELECT * FROM employees".as(parser.*)
    }.take(5)

    Ok(s"Test database call $res")
  }

  def employee(id: Int) = Action {
    val res = db.withConnection { implicit c =>
      SQL""" 
        SELECT first_name, last_name, dept_no 
        FROM employees e
        JOIN dept_emp d ON d.emp_no = e.emp_no 
        WHERE e.emp_no = $id
      """.as(parser.*)
    }.head

    Ok(s"Employee with id: $id: $res")
  }

  def employeeCount() = Action {
    val count: Long = db.withConnection { implicit c => 
      SQL"SELECT count(*) FROM employees".as(scalar[Long].single)
    }

    Ok(s"Number of employees: $count")
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

  def testMacro() = Action {
    Ok(s"TestClass output ${tl.simpleMethod()}")
  }

}
