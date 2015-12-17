
import scala.concurrent.Future

import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.Play
import play.api.Play.current
import play.Logger

import com.google.inject._

//module locations
import service.equipment.slick.EquipmentModule

object Global extends GlobalSettings {

  override def onHandlerNotFound(request: RequestHeader): Future[Result] = {
    Logger.debug("Not Found: " + request.toString)
    Future.successful(NotFound)
  }

  override def onBadRequest(request: RequestHeader, error: String): Future[Result] = {
    Logger.debug("Bad Request: " + request.toString)
    Future.successful(BadRequest(error))
  }
  
  private lazy val injector = {
    Guice.createInjector(new EquipmentModule)
  }

  override def getControllerInstance[A](clazz: Class[A]): A = {
    injector.getInstance(clazz)
  }
}