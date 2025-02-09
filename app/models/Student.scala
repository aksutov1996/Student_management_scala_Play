package models

import play.api.libs.json._
import slick.jdbc.PostgresProfile.api._

case class Student(id: Option[Int]
                   , lastName: String
                   , firstName: String
                   , middleName: String
                   , group: String
                   , averageMark: Double
                  )

object Student {
  // Сериализация и десериализация модели в JSON
  implicit val studentFormat: OFormat[Student] = Json.format[Student]

  // Таблица студентов в базе данных
  /*class Students(tag: Tag) extends Table[Student](tag, "students") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)  // Автоматически увеличиваемый ID
    def lastName = column[String]("last_name")
    def firstName = column[String]("first_name")
    def middleName = column[String]("middle_name")
    def group = column[String]("group")
    def averageMark = column[Double]("average_mark")

    def * = (id.?, lastName, firstName, middleName, group, averageMark).mapTo[Student]
  }

  // Мы используем TableQuery для работы с таблицей студентов
  val students = TableQuery[Students]*/
}
