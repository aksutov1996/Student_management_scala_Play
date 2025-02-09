package controllers

import javax.inject._
import play.api.mvc._
import play.api.Configuration
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthController @Inject()(config: Configuration
                               , mcc: MessagesControllerComponents
                               //, ws: WSClient, // Инжектируем WSClient для выполнения HTTP-запросов
                              )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(mcc) {

  private lazy val pathAuthorizationUrl = "silhouette.authenticator.oauth2.authorizationUrl"
  private lazy val pathClientId = "silhouette.authenticator.oauth2.clientId"
  private lazy val pathClientSecret = "silhouette.authenticator.oauth2.clientSecret"
  private lazy val pathTokenUrl = "silhouette.authenticator.oauth2.tokenUrl"
  private lazy val keyGetCodeAutorize = "code"
  private lazy val keyToken = "access_token"

  // Получаем URL авторизации и токена из конфигурации
  private lazy val clientId = config.get[String](pathClientId)
  private lazy val clientSecret = config.get[String](pathClientSecret)
  private lazy val authorizationUrl = config.get[String](pathAuthorizationUrl)
  private lazy val tokenUrl = config.get[String](pathTokenUrl)

  /** Эндпоинт для авторизации (перенаправление на сервер авторизации) */
  def authorize = Action.async {
    // Перенаправление пользователя на страницу авторизации
    Future.successful(Redirect(authorizationUrl))
  }

  /**
   * Эндпоинт для получения access token после авторизации
   *
   * данный эндпоинт указывается при указании эндпоинта авторизации,
   * чтобы сервер авторизации направил пользователя в этот эндпоинт
   */
  def callback = Action.async { request =>
    // Получаем код авторизации из запроса
    request.getQueryString(keyGetCodeAutorize) match {
      case Some(code) =>
        // Возвращаем access token в ответе
        exchangeCodeForToken(code) match {
          case Some(token) => Future.successful(Ok(Json.obj(keyToken -> token)))
          case _ => Future.successful(NotFound(s"No token response"))
        }
      case _ => Future.successful(NotFound(s"No authorization response"))
    }
  }

  /** Метод для обмена кода на токен */
  private def exchangeCodeForToken(code: String): Option[String] = {
    /** Тут будет логика обмена кода на токен через внешний OAuth2 сервер
     * В реальном приложении нужно будет:
     * отправить запрос к серверу OAuth2 по [[tokenUrl]]
     * передать параметры [[code]], [[clientId]], [[clientSecret]]
     * и получить access token */
    /*ws.url(tokenUrl)
      .post {
        Json.obj(
          "client_id" -> clientId,
          "client_secret" -> clientSecret,
          "code" -> code
        )
      }*/

    // имитация ответа
    Option("sample-access-token")
  }
}
