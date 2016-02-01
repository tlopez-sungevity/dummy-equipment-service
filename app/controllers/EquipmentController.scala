package controllers

import play.api._
import play.api.mvc._
import play.api.Logger
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

import com.sungevity.commons.formats.siren.spec.serializable.{EntitySpecIsSirenEntitySerializable, SirenSpecIsSirenEntitySerializable}
import com.sungevity.commons.formats.siren.{SirenSubEntity, SirenEntity}
import com.sungevity.commons.formats.siren.Implicits._
import serialize.equipment.Implicits._
import service.equipment._

import scala.concurrent.Future


class EquipmentController @Inject() (equipmentService: EquipmentService) extends Controller {
  private def toNotFoundError(id: Int): JsValue = {
    Json.obj("message" -> s"Unable to find any equipment for ID $id.")
  }

  //TODO clean up logging and error handling

  def getEquipment(id: Int): Action[AnyContent] = Action.async {
    equipmentService.getEquipment(new EquipmentIdentity(id)) map {
      case Some(module: Module) =>
        val serializedResponseBody = Json.toJson(module toSirenEntity)
        Logger.info(s"response -> $serializedResponseBody")
        Ok(serializedResponseBody)
      case _ =>  NotFound(toNotFoundError(id))
    } recover {
      case e: IllegalStateException =>
        val msg = s"Unable to map result for Equipment ID $id to known type."
        Logger.error(msg,e)
        //TODO error response
        InternalServerError(Json.obj("message" -> msg))
      case f =>
        Logger.error(s"Exception while finding equipoment for ID $id", f)
        InternalServerError(Json.obj("message" -> "Internal Server Error"))

    }
  }





  def dummyGetInverterProductSpec = Action.async { req =>
    import EntitySpecIsSirenEntitySerializable.{toSirenEntity => c1}

    val specEntity: SirenEntity = formats.siren.inverter.inverterProductSoloEntitySpec

      Future.successful(Ok(Json.toJson(specEntity))
        .withHeaders(CONTENT_TYPE -> "application/vnd.siren+json"))
  }

  def dummyGetModuleProductSpec = Action.async { req =>
    import EntitySpecIsSirenEntitySerializable.{toSirenEntity => c1}

    val specEntity: SirenEntity = formats.siren.module.moduleProductSoloEntitySpec

    Future.successful(Ok(Json.toJson(specEntity))
      .withHeaders(CONTENT_TYPE -> "application/vnd.siren+json"))
  }

}
