package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json

class StudentSpec extends PlaySpec {
  "Student" should {
    val gStudent = Student(id = Some(1)
      , lastName = "Коноплянко"
      , firstName = "Игорь"
      , middleName = "Петрович"
      , group = "777"
      , averageMark = 4.3
    )

    val gJson = Json.parse(
      """{"id":1,"lastName":"Коноплянко","firstName":"Игорь","middleName":"Петрович","group":"777","averageMark":4.3}"""
    )

    "Student serialization" in {
      Json.toJson(gStudent) mustBe gJson
    }

    "Student deserialization" in {
      gJson.as[Student] mustBe gStudent
    }
  }
}
