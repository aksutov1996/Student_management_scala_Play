package dao

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import models.Student
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.{TableQuery, Tag}
import slick.jdbc.PostgresProfile.api._

class StudentDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider
                          )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  // Мы используем TableQuery для работы с таблицей студентов
  private val students = TableQuery[StudentsTable]

  def all(): Future[Seq[Student]] = db.run {
    students.result
  }

  def get(id: Int): Future[Option[Student]] = db.run {
    students
      .filter(_.id === id)
      .result.headOption
  }

  def update(id: Int, student: Student): Future[Int] = db.run {
    students
      .filter(_.id === id)
      .update(student)
  }

  def insert(student: Student): Future[Int] = db.run {
    students += student
  }

  def delete(id: Int): Future[Int] = db.run {
    students
      .filter(_.id === id)
      .delete
  }

  private class StudentsTable(tag: Tag) extends Table[Student](tag, "students") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // Автоматически увеличиваемый ID

    def lastName = column[String]("last_name")

    def firstName = column[String]("first_name")

    def middleName = column[String]("middle_name")

    def group = column[String]("group")

    def averageMark = column[Double]("average_mark")

    def * = (id.?, lastName, firstName, middleName, group, averageMark).mapTo[Student]
  }
}
