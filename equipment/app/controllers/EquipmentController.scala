package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global

import services.equipment.{EquipmentService, Equipment}

class EquipmentController @Inject() (equipmentService: EquipmentService) extends Controller {

	implicit val equipmentWrites = Json.writes[Equipment]

	def getEquipment(id: Int) = Action.async {
  		equipmentService.getEquipment(id) map { 
  			case Some(equipment) => Ok(Json.toJson(equipment))
  			case _ =>  NotFound("Nothing found")
  		}
  	}

}
