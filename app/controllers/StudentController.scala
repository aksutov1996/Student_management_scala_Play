package controllers

import dao.StudentDAO

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class StudentController @Inject()(studentDAO: StudentDAO//protected val dbConfigProvider: DatabaseConfigProvider
                                  , mcc: MessagesControllerComponents
                                 )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(mcc) {

  /** Получение всех студентов */
  def getAll = Action.async {
    studentDAO.all().map { students =>
      Ok(Json.toJson(students))
    }.recover {
      case e: Exception => InternalServerError(s"Error: ${e.getMessage}")
    }
  }

  /** Получение студента по ID */
  def get(id: Int) = Action.async {
    studentDAO.get(id).map {
      case Some(student) => Ok(Json.toJson(student))
      case _ => NotFound(s"Student with id $id not found")
    }.recover {
      case e: Exception => InternalServerError(s"Error: ${e.getMessage}")
    }
  }

  /** Добавление нового студента */
  def insert = Action.async(parse.json) { request =>
    request.body.validate[Student].fold(
      errors => Future.successful(BadRequest("Invalid student data")),
      student => {
        studentDAO.insert(student).map { _ =>
          Created(Json.toJson(student))
        }.recover {
          case e: Exception => InternalServerError(s"Error: ${e.getMessage}")
        }
      }
    )
  }

  /** Редактирование существующего студента */
  def update(id: Int) = Action.async(parse.json) { request =>
    // Валидация данных из тела запроса
    request.body.validate[Student].fold(
      errors => Future.successful(BadRequest("Invalid student data")), // Ошибка валидации данных
      studentData => {
        // Выполнение запроса для нахождения студента по id
        studentDAO.get(id).flatMap {
          // Если студент найден, обновляем его данные
          case Some(existingStudent) =>
            val updatedStudent = existingStudent.copy(
              lastName = studentData.lastName,
              firstName = studentData.firstName,
              middleName = studentData.middleName,
              group = studentData.group,
              averageMark = studentData.averageMark
            )

            // Обновление записи в базе
            studentDAO.update(id, updatedStudent).map {
              case 0 => NotFound(s"Student with id $id not found")
              case _ => Ok(Json.toJson(updatedStudent))
            }.recover {
              case e: Exception => InternalServerError(s"Error updating student: ${e.getMessage}")
            }

          case _ =>
            Future.successful(NotFound(s"Student with id $id not found"))
        }
      }
    )
  }

  /** Удаление студента */
  def delete(id: Int) = Action.async {
    studentDAO.delete(id).map {
      case 0 => NotFound(s"Student with id $id not found")
      case _ => NoContent
    }.recover {
      case e: Exception => InternalServerError(s"Error: ${e.getMessage}")
    }
  }
}