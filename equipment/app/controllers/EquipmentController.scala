package controllers

import play.api._
import play.api.mvc._
import play.api.Logger
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

import services.equipment.{EquipmentService, Equipment, Inverter, Module}

import com.sungevity.commons.formats.siren._
import com.sungevity.commons.formats.siren.Implicits._

class EquipmentController @Inject() (equipmentService: EquipmentService) extends Controller {

  private val iso8061Format = "yyyy-MM-dd'T'HH:mm:ss"

  implicit val inverterWrites: Writes[Inverter] = (
      (JsPath \ "id").write[Int] and
      (JsPath \ "modelName").write[String] and
      (JsPath \ "manufacturerName").write[String] and
      (JsPath \ "description").write[Option[String]] and
      (JsPath \ "modifiedDate").write[DateTime](Writes.jodaDateWrites(iso8061Format)) and
      (JsPath \ "rating").write[Option[Double]] and
      (JsPath \ "efficiency").write[Double] and
      (JsPath \ "outputVoltage").writeNullable[Double] and
      (JsPath \ "isThreePhase").writeNullable[Boolean]
  )(unlift(Inverter.unapply))

  implicit val moduleWrites: Writes[Module] = (
      (JsPath \ "id").write[Int] and
      (JsPath \ "modelName").write[String] and
      (JsPath \ "manufacturerName").write[String] and
      (JsPath \ "description").writeNullable[String] and
      (JsPath \ "modifiedDate").write[DateTime](Writes.jodaDateWrites(iso8061Format)) and
      (JsPath \ "kwStc").write[Double] and
      (JsPath \ "kwPtc").write[Double] and
      (JsPath \ "heightMm").write[Double] and
      (JsPath \ "widthMm").write[Double] and
      (JsPath \ "isBipvRated").writeNullable[Boolean] and
      (JsPath \ "powerTemperatureCoefficient").write[Double] and
      (JsPath \ "normalOperatingCellTemperature").write[Double]
  )(unlift(Module.unapply))

  private def toSiren(inverter: Inverter): JsValue = {
    Json.toJson(SirenEntity(
      `class`=List("equipment","equipment-inverter"),
      properties=Some(Json.toJson(inverter)),
      title=Some(s"${inverter.manufacturerName} ${inverter.modelName}")))
  }

  private def toSiren(module: Module): JsValue = {
    Json.toJson(SirenEntity(
      `class`=List("equipment", "equipment-module"),
      properties=Some(Json.toJson(module)),
      title=Some(s"${module.manufacturerName} ${module.modelName}")))
  }

  private def toNotFoundError(id: Int): JsValue = {
    Json.obj("message" -> s"Unable to find any equipment for ID $id.")
  }

  def getEquipment(id: Int): Action[AnyContent] = Action.async {
      equipmentService.getEquipment(id) map {
        case Some(inverter: Inverter) => Ok(toSiren(inverter))
        case Some(module: Module) => Ok(toSiren(module))
        case _ =>  NotFound(toNotFoundError(id))
      } recover {
        case e:IllegalStateException => 
          val msg = s"Unable to map result for Equipment ID $id to known type."
          Logger.error(msg,e)
          //TODO error response
          InternalServerError(Json.obj("message" -> msg))
      }
    }

}
