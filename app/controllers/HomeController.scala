package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import models.MovieRepository
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(
    cc: ControllerComponents,
    movieRepository: MovieRepository
) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  /*
    FunciÃ³n de ayuda para crear la tabla si esta aÃºn no existe.
   */
  def dbInit() = Action.async { request =>
    val logger = play.Logger.of("MovieController")

    movieRepository.dbInit
      .map(_ => Created("Tabla creada"))
      .recover {
        case ex =>
          logger.error("Falla en dbInit", ex)
          InternalServerError(s"Hubo un error: ${ex.getLocalizedMessage}")
      }
  }
}
