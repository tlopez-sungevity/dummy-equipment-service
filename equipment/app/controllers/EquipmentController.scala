package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

import services.equipment.{EquipmentService, Equipment, Inverter, Module}

import com.sungevity.commons.formats.siren._
import com.sungevity.commons.formats.siren.Implicits._

class EquipmentController @Inject() (equipmentService: EquipmentService) extends Controller {

	implicit val inverterWrites = Json.writes[Inverter]
	implicit val moduleWrites = Json.writes[Module]

	private def toSiren(inverter: Inverter): JsValue = {
		Json.toJson(SirenEntity(
			theClass=Set("equipment","equipment-inverter"),
			properties=Some(Json.toJson(inverter)),
			title=Some(s"${inverter.model}")))
	}

	private def toSiren(module: Module): JsValue = {
		Json.toJson(SirenEntity(
			theClass=Set("equipment", "equipment-module"),
			properties=Some(Json.toJson(module)),
			title=Some(s"${module.model}")))
	}


	def getEquipment(id: Int) = Action.async {
  		equipmentService.getEquipment(id) map { 
  			case Some(inverter: Inverter) => Ok(toSiren(inverter))
  			case Some(module: Module) => Ok(toSiren(module))
  			case _ =>  NotFound("Nothing found")
  		}
  	}



}
